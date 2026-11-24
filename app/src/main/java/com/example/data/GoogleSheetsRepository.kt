package com.example.data

import com.example.model.ActivityRecord
import com.example.model.AralGrade
import com.example.model.GradeProgressState
import com.example.model.LearnerProfile
import com.example.model.LearnerProgressRecord
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

data class SpreadsheetHandle(
  val spreadsheetId: String,
  val spreadsheetUrl: String,
  val tabTitlesByGrade: Map<AralGrade, String>,
) {
  fun tabFor(grade: AralGrade): String =
    tabTitlesByGrade[grade] ?: grade.sheetTabName
}

data class CentralSyncResult(
  val spreadsheetUrl: String?,
  val byGrade: Map<AralGrade, List<LearnerProgressRecord>>,
)

data class CentralUpsertResult(
  val rowNumber: Int,
  val spreadsheetUrl: String?,
)

class GoogleSheetsRepository {
  private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

  private val client = OkHttpClient.Builder()
    .connectTimeout(20, TimeUnit.SECONDS)
    .readTimeout(25, TimeUnit.SECONDS)
    .writeTimeout(25, TimeUnit.SECONDS)
    .build()

  suspend fun fetchUserEmail(accessToken: String): String? = withContext(Dispatchers.IO) {
    val request = Request.Builder()
      .url("https://www.googleapis.com/oauth2/v2/userinfo")
      .header("Authorization", "Bearer $accessToken")
      .get()
      .build()
    runCatching {
      client.newCall(request).execute().use { resp ->
        if (!resp.isSuccessful) return@use null
        val body = resp.body?.string().orEmpty()
        JSONObject(body).optString("email").takeIf { it.isNotBlank() }
      }
    }.getOrNull()
  }

  suspend fun findOrCreateSpreadsheet(
    accessToken: String,
    savedSpreadsheetId: String?,
    connectedEmail: String?,
  ): SpreadsheetHandle = withContext(Dispatchers.IO) {
    if (!savedSpreadsheetId.isNullOrBlank()) {
      val existing = getSpreadsheetHandle(accessToken, savedSpreadsheetId)
      if (existing != null) {
        val ensured = ensureAllGradeTabsAndHeaders(accessToken, existing)
        return@withContext ensured
      }
    }

    val foundId = searchExistingSpreadsheetInDrive(accessToken)
    if (!foundId.isNullOrBlank()) {
      val existing = getSpreadsheetHandle(accessToken, foundId)
      if (existing != null) {
        val ensured = ensureAllGradeTabsAndHeaders(accessToken, existing)
        return@withContext ensured
      }
    }

    val created = createSpreadsheet(accessToken)
    for (grade in com.example.model.AralGrade.entries) {
      writeAndStyleHeaders(accessToken, created, grade)
    }
    if (!connectedEmail.equals(SheetCoordinateHelper.TEACHER_EMAIL, ignoreCase = true)) {
      shareSpreadsheetWithTeacher(accessToken, created.spreadsheetId)
    }
    created
  }

  private fun getSpreadsheetHandle(accessToken: String, spreadsheetId: String): SpreadsheetHandle? {
    val url = "https://sheets.googleapis.com/v4/spreadsheets/$spreadsheetId?fields=spreadsheetId,spreadsheetUrl,sheets.properties"
    val request = Request.Builder()
      .url(url)
      .header("Authorization", "Bearer $accessToken")
      .get()
      .build()
    return runCatching {
      client.newCall(request).execute().use { resp ->
        if (!resp.isSuccessful) return@use null
        val json = JSONObject(resp.body?.string().orEmpty())
        val sheetsArr = json.optJSONArray("sheets")
        val existingTitles = mutableListOf<String>()
        if (sheetsArr != null) {
          for (i in 0 until sheetsArr.length()) {
            val t = sheetsArr.optJSONObject(i)?.optJSONObject("properties")?.optString("title").orEmpty()
            if (t.isNotBlank()) existingTitles.add(t)
          }
        }
        val tabMap = mutableMapOf<AralGrade, String>()
        for (grade in AralGrade.entries) {
          val match = existingTitles.firstOrNull { it == grade.sheetTabName }
            ?: existingTitles.firstOrNull { it in grade.legacyTabNames }
          if (match != null) {
            tabMap[grade] = match
          }
        }
        SpreadsheetHandle(
          spreadsheetId = json.getString("spreadsheetId"),
          spreadsheetUrl = json.optString("spreadsheetUrl", "https://docs.google.com/spreadsheets/d/$spreadsheetId"),
          tabTitlesByGrade = tabMap,
        )
      }
    }.getOrNull()
  }

  private fun searchExistingSpreadsheetInDrive(accessToken: String): String? {
    val q = "name='${SheetCoordinateHelper.SPREADSHEET_TITLE}' and mimeType='application/vnd.google-apps.spreadsheet' and trashed=false"
    val encodedQ = URLEncoder.encode(q, "UTF-8")
    val url = "https://www.googleapis.com/drive/v3/files?q=$encodedQ&fields=files(id,name)&orderBy=modifiedTime%20desc&pageSize=1"
    val request = Request.Builder()
      .url(url)
      .header("Authorization", "Bearer $accessToken")
      .get()
      .build()
    return runCatching {
      client.newCall(request).execute().use { resp ->
        if (!resp.isSuccessful) return@use null
        val json = JSONObject(resp.body?.string().orEmpty())
        val files = json.optJSONArray("files") ?: return@use null
        if (files.length() == 0) return@use null
        files.getJSONObject(0).optString("id").takeIf { it.isNotBlank() }
      }
    }.getOrNull()
  }

  private fun createSpreadsheet(accessToken: String): SpreadsheetHandle {
    val sheetsArr = JSONArray().apply {
      for (grade in com.example.model.AralGrade.entries) {
        put(JSONObject().apply {
          put("properties", JSONObject().apply {
            put("title", grade.sheetTabName)
            put("gridProperties", JSONObject().apply {
              put("rowCount", 500)
              put("columnCount", maxOf(80, SheetCoordinateHelper.totalColumnsFor(grade) + 2))
              put("frozenRowCount", 3)
              put("frozenColumnCount", 1)
            })
          })
        })
      }
    }
    val bodyJson = JSONObject().apply {
      put("properties", JSONObject().apply {
        put("title", SheetCoordinateHelper.SPREADSHEET_TITLE)
      })
      put("sheets", sheetsArr)
    }

    val request = Request.Builder()
      .url("https://sheets.googleapis.com/v4/spreadsheets")
      .header("Authorization", "Bearer $accessToken")
      .post(bodyJson.toString().toRequestBody(jsonMediaType))
      .build()

    client.newCall(request).execute().use { resp ->
      val text = resp.body?.string().orEmpty()
      if (!resp.isSuccessful) {
        throw IllegalStateException("Could not create Google Sheet (${resp.code}): ${extractApiError(text)}")
      }
      val json = JSONObject(text)
      val id = json.getString("spreadsheetId")
      return SpreadsheetHandle(
        spreadsheetId = id,
        spreadsheetUrl = json.optString("spreadsheetUrl", "https://docs.google.com/spreadsheets/d/$id"),
        tabTitlesByGrade = com.example.model.AralGrade.entries.associateWith { it.sheetTabName },
      )
    }
  }

  private fun ensureAllGradeTabsAndHeaders(accessToken: String, handle: SpreadsheetHandle): SpreadsheetHandle {
    val updatedMap = handle.tabTitlesByGrade.toMutableMap()
    val missingGrades = AralGrade.entries.filter { !updatedMap.containsKey(it) }
    if (missingGrades.isNotEmpty()) {
      val requestsArr = JSONArray()
      for (g in missingGrades) {
        requestsArr.put(JSONObject().apply {
          put("addSheet", JSONObject().apply {
            put("properties", JSONObject().apply {
              put("title", g.sheetTabName)
              put("index", g.ordinal)
              put("gridProperties", JSONObject().apply {
                put("rowCount", 500)
                put("columnCount", maxOf(80, SheetCoordinateHelper.totalColumnsFor(g) + 2))
                put("frozenRowCount", 3)
                put("frozenColumnCount", 1)
              })
            })
          })
        })
      }
      val body = JSONObject().apply { put("requests", requestsArr) }
      val req = Request.Builder()
        .url("https://sheets.googleapis.com/v4/spreadsheets/${handle.spreadsheetId}:batchUpdate")
        .header("Authorization", "Bearer $accessToken")
        .post(body.toString().toRequestBody(jsonMediaType))
        .build()
      runCatching {
        client.newCall(req).execute().use { resp ->
          if (resp.isSuccessful) {
            for (g in missingGrades) {
              updatedMap[g] = g.sheetTabName
            }
          }
        }
      }
    }
    val finalHandle = handle.copy(tabTitlesByGrade = updatedMap)
    for (g in AralGrade.entries) {
      if (finalHandle.tabTitlesByGrade.containsKey(g)) {
        ensureHeadersPresent(accessToken, finalHandle, g)
      }
    }
    return finalHandle
  }

  private fun encodeRangePath(tabTitle: String, cellsRange: String): String {
    val quotedTab = "'${tabTitle.replace("'", "''")}'"
    return URLEncoder.encode("$quotedTab!$cellsRange", "UTF-8").replace("+", "%20")
  }

  private fun ensureHeadersPresent(
    accessToken: String,
    handle: SpreadsheetHandle,
    grade: com.example.model.AralGrade,
  ) {
    val tab = handle.tabFor(grade)
    val range = encodeRangePath(tab, "A3:B3")
    val request = Request.Builder()
      .url("https://sheets.googleapis.com/v4/spreadsheets/${handle.spreadsheetId}/values/$range")
      .header("Authorization", "Bearer $accessToken")
      .get()
      .build()
    val hasHeader = runCatching {
      client.newCall(request).execute().use { resp ->
        if (!resp.isSuccessful) return@use false
        val values = JSONObject(resp.body?.string().orEmpty()).optJSONArray("values")
        values != null && values.length() > 0 && values.optJSONArray(0)?.optString(0).orEmpty().isNotBlank()
      }
    }.getOrDefault(false)
    if (!hasHeader) {
      writeAndStyleHeaders(accessToken, handle, grade)
    }
  }

  private fun writeAndStyleHeaders(
    accessToken: String,
    handle: SpreadsheetHandle,
    grade: com.example.model.AralGrade,
  ) {
    val tab = handle.tabFor(grade)
    val lastCol = SheetCoordinateHelper.lastColumnLetters(grade)
    val rows = SheetCoordinateHelper.buildHeaderRows(grade)
    val valuesArr = JSONArray().apply {
      rows.forEach { row -> put(JSONArray(row)) }
    }
    val payload = JSONObject().apply {
      put("values", valuesArr)
    }
    val range = encodeRangePath(tab, "A1:${lastCol}3")
    val url = "https://sheets.googleapis.com/v4/spreadsheets/${handle.spreadsheetId}/values/$range?valueInputOption=USER_ENTERED"
    val request = Request.Builder()
      .url(url)
      .header("Authorization", "Bearer $accessToken")
      .put(payload.toString().toRequestBody(jsonMediaType))
      .build()
    client.newCall(request).execute().use { resp ->
      if (!resp.isSuccessful) {
        val text = resp.body?.string().orEmpty()
        throw IllegalStateException("Header write failed (${resp.code}): ${extractApiError(text)}")
      }
    }
  }

  private fun shareSpreadsheetWithTeacher(accessToken: String, fileId: String) {
    val body = JSONObject().apply {
      put("type", "user")
      put("role", "writer")
      put("emailAddress", SheetCoordinateHelper.TEACHER_EMAIL)
    }
    val request = Request.Builder()
      .url("https://www.googleapis.com/drive/v3/files/$fileId/permissions?sendNotificationEmail=false")
      .header("Authorization", "Bearer $accessToken")
      .post(body.toString().toRequestBody(jsonMediaType))
      .build()
    runCatching {
      client.newCall(request).execute().close()
    }
  }

  suspend fun readAllLearners(
    accessToken: String,
    handle: SpreadsheetHandle,
    grade: com.example.model.AralGrade = com.example.model.AralGrade.GRADE_4,
  ): List<LearnerProgressRecord> = withContext(Dispatchers.IO) {
    val tab = handle.tabFor(grade)
    val lastCol = SheetCoordinateHelper.lastColumnLetters(grade)
    val range = encodeRangePath(tab, "A4:${lastCol}500")
    val url = "https://sheets.googleapis.com/v4/spreadsheets/${handle.spreadsheetId}/values/$range"
    val request = Request.Builder()
      .url(url)
      .header("Authorization", "Bearer $accessToken")
      .get()
      .build()

    val maxActs = grade.sheetColumnSlots
    val metaNameIdx = SheetCoordinateHelper.colMetaName(grade)
    val metaGradeIdx = SheetCoordinateHelper.colMetaGrade(grade)
    val metaSchoolIdx = SheetCoordinateHelper.colMetaSchool(grade)
    val metaDeviceIdx = SheetCoordinateHelper.colMetaDeviceId(grade)
    val metaResumeIdx = SheetCoordinateHelper.colMetaResume(grade)
    val gradeCatalog = WorksheetCatalog.getWorksheets(grade).associateBy { it.id }

    client.newCall(request).execute().use { resp ->
      val text = resp.body?.string().orEmpty()
      if (!resp.isSuccessful) {
        throw IllegalStateException("Could not read Google Sheet (${resp.code}): ${extractApiError(text)}")
      }
      val json = JSONObject(text)
      val rows = json.optJSONArray("values") ?: return@withContext emptyList()
      val learners = mutableListOf<LearnerProgressRecord>()
      for (i in 0 until rows.length()) {
        val rowArr = rows.optJSONArray(i) ?: continue
        val rowNumber = SheetCoordinateHelper.FIRST_LEARNER_ROW + i
        val colA = rowArr.optString(0).trim()
        val metaName = rowArr.optString(metaNameIdx).trim()
        if (colA.isBlank() && metaName.isBlank()) continue

        val parsedFromA = colA.split("|").map { it.trim() }
        val name = metaName.ifBlank { parsedFromA.getOrNull(0).orEmpty() }
        if (name.isBlank()) continue
        val gradeText = rowArr.optString(metaGradeIdx).trim()
          .ifBlank { parsedFromA.getOrNull(1).orEmpty().ifBlank { grade.shortLabel } }
        val school = rowArr.optString(metaSchoolIdx).trim()
          .ifBlank { parsedFromA.getOrNull(2).orEmpty().ifBlank { "Bagacay Elementary School" } }
        val deviceId = rowArr.optString(metaDeviceIdx).trim()
          .ifBlank { parsedFromA.getOrNull(3).orEmpty() }

        val resumeRaw = rowArr.optString(metaResumeIdx).trim()
        val resumeParts = resumeRaw.split(":")
        val lastWs = resumeParts.getOrNull(0)?.toIntOrNull()?.coerceIn(1, maxActs) ?: 1
        val lastItem = resumeParts.getOrNull(1)?.toIntOrNull()?.coerceAtLeast(0) ?: 0

        val activityMap = mutableMapOf<Int, ActivityRecord>()
        for (actId in 1..maxActs) {
          val scoreRaw = rowArr.optString(SheetCoordinateHelper.activityScoreColumnIndex(actId, maxActs)).trim()
          if (scoreRaw.isBlank()) continue
          val fallbackTotal = gradeCatalog[actId]?.itemCount ?: 20
          val parsedScore = SheetCoordinateHelper.parseScoreFromSheet(scoreRaw, fallbackTotal) ?: continue
          val tsRaw = rowArr.optString(SheetCoordinateHelper.activityTimestampColumnIndex(actId, maxActs)).trim()
          val pct = SheetCoordinateHelper.computePercent(parsedScore.first, parsedScore.second)
          val stars = when {
            pct >= 90 -> 3
            pct >= 50 -> 2
            parsedScore.first > 0 -> 1
            else -> 0
          }
          activityMap[actId] = ActivityRecord(
            worksheetId = actId,
            correctCount = parsedScore.first,
            totalItems = parsedScore.second,
            stars = stars,
            timestamp = tsRaw,
            syncedToSheet = true,
            completedRound = true,
          )
        }

        val gpState = com.example.model.GradeProgressState(
          records = activityMap,
          lastWorksheetId = lastWs,
          lastItemIndex = lastItem,
          pendingSheetSync = false,
          sheetRowNumber = rowNumber,
        )
        learners.add(
          LearnerProgressRecord(
            profile = LearnerProfile(
              name = name,
              gradeLevel = gradeText,
              school = school,
              deviceAddress = deviceId,
              sheetRowNumber = if (grade == com.example.model.AralGrade.GRADE_4) rowNumber else null,
            ),
            records = activityMap,
            lastWorksheetId = lastWs,
            lastItemIndex = lastItem,
            pendingSheetSync = false,
            activeGrade = grade,
            gradeProgress = mapOf(grade to gpState),
          ),
        )
      }
      learners
    }
  }

  suspend fun upsertLearnerRow(
    accessToken: String,
    handle: SpreadsheetHandle,
    record: LearnerProgressRecord,
    grade: com.example.model.AralGrade = record.activeGrade,
  ): Int = withContext(Dispatchers.IO) {
    val tab = handle.tabFor(grade)
    val maxActs = grade.sheetColumnSlots
    val gp = record.progressFor(grade)
    val existingLearners = runCatching { readAllLearners(accessToken, handle, grade) }.getOrDefault(emptyList())
    val matched = existingLearners.firstOrNull {
      it.profile.normalizedKey == record.profile.normalizedKey
    }
    val targetRow = gp.sheetRowNumber
      ?: matched?.progressFor(grade)?.sheetRowNumber
      ?: ((existingLearners.maxOfOrNull { it.progressFor(grade).sheetRowNumber ?: 3 } ?: 3) + 1)
        .coerceAtLeast(SheetCoordinateHelper.FIRST_LEARNER_ROW)

    val dataUpdates = JSONArray()

    // Column A: Learner Info
    dataUpdates.put(JSONObject().apply {
      put("range", "$tab!A$targetRow")
      put("values", JSONArray().apply {
        put(JSONArray().apply {
          put("${record.profile.name} | ${record.profile.gradeLevel} | ${record.profile.school} | ${record.profile.deviceAddress}")
        })
      })
    })

    // Activity pairs: B4/C4 (Act 1), D4/E4 (Act 2), ...
    for ((actId, act) in gp.records) {
      if (actId !in 1..maxActs) continue
      val scoreCell = SheetCoordinateHelper.activityScoreCell(actId, targetRow, maxActs)
      val tsCell = SheetCoordinateHelper.activityTimestampCell(actId, targetRow, maxActs)
      dataUpdates.put(JSONObject().apply {
        put("range", "$tab!$scoreCell:$tsCell")
        put("values", JSONArray().apply {
          put(JSONArray().apply {
            put(SheetCoordinateHelper.formatScoreForSheet(act.correctCount, act.totalItems))
            put(act.timestamp)
          })
        })
      })
    }

    // Structured metadata columns
    val startMetaCol = SheetCoordinateHelper.columnIndexToLetters(SheetCoordinateHelper.colMetaName(grade))
    val endMetaCol = SheetCoordinateHelper.columnIndexToLetters(SheetCoordinateHelper.colMetaCompleted(grade))
    dataUpdates.put(JSONObject().apply {
      put("range", "$tab!$startMetaCol$targetRow:$endMetaCol$targetRow")
      put("values", JSONArray().apply {
        put(JSONArray().apply {
          put(record.profile.name)
          put(record.profile.gradeLevel)
          put(record.profile.school)
          put(record.profile.deviceAddress)
          put("${gp.lastWorksheetId}:${gp.lastItemIndex}")
          put("${record.completedCountFor(grade)}/${grade.totalWorksheets}")
        })
      })
    })

    val payload = JSONObject().apply {
      put("valueInputOption", "USER_ENTERED")
      put("data", dataUpdates)
    }

    val request = Request.Builder()
      .url("https://sheets.googleapis.com/v4/spreadsheets/${handle.spreadsheetId}/values:batchUpdate")
      .header("Authorization", "Bearer $accessToken")
      .post(payload.toString().toRequestBody(jsonMediaType))
      .build()

    client.newCall(request).execute().use { resp ->
      val text = resp.body?.string().orEmpty()
      if (!resp.isSuccessful) {
        throw IllegalStateException("Failed saving row $targetRow (${resp.code}): ${extractApiError(text)}")
      }
    }
    targetRow
  }

  private fun extractApiError(body: String): String {
    return runCatching {
      val json = JSONObject(body)
      json.optJSONObject("error")?.optString("message").orEmpty().ifBlank {
        json.optString("error").ifBlank { body.take(160) }
      }
    }.getOrDefault(body.take(160))
  }

  suspend fun readAllGradesFromWebApp(webAppUrl: String): CentralSyncResult = withContext(Dispatchers.IO) {
    val sep = if ('?' in webAppUrl) "&" else "?"
    val url = "${webAppUrl.trim()}${sep}action=readAllGrades"
    val request = Request.Builder()
      .url(url)
      .get()
      .build()

    client.newCall(request).execute().use { resp ->
      val text = resp.body?.string().orEmpty()
      if (!resp.isSuccessful) {
        throw IllegalStateException("Central Sheet sync failed (HTTP ${resp.code}): ${extractApiError(text)}")
      }
      val json = parseWebAppJsonResponse(text)
      val ssUrl = json.optString("spreadsheetUrl").takeIf { it.isNotBlank() }
      val byGradeObj = json.optJSONObject("byGrade")
      val resultMap = mutableMapOf<AralGrade, List<LearnerProgressRecord>>()
      for (grade in AralGrade.entries) {
        val arr = byGradeObj?.optJSONArray(grade.name)
        resultMap[grade] = if (arr != null) parseWebAppLearnersArray(arr, grade) else emptyList()
      }
      CentralSyncResult(spreadsheetUrl = ssUrl, byGrade = resultMap)
    }
  }

  suspend fun readGradeFromWebApp(
    webAppUrl: String,
    grade: AralGrade,
  ): Pair<String?, List<LearnerProgressRecord>> = withContext(Dispatchers.IO) {
    val sep = if ('?' in webAppUrl) "&" else "?"
    val url = "${webAppUrl.trim()}${sep}action=readAll&grade=${grade.name}"
    val request = Request.Builder()
      .url(url)
      .get()
      .build()

    client.newCall(request).execute().use { resp ->
      val text = resp.body?.string().orEmpty()
      if (!resp.isSuccessful) {
        throw IllegalStateException("Central Sheet read failed (HTTP ${resp.code}): ${extractApiError(text)}")
      }
      val json = parseWebAppJsonResponse(text)
      val ssUrl = json.optString("spreadsheetUrl").takeIf { it.isNotBlank() }
      val arr = json.optJSONArray("learners") ?: JSONArray()
      ssUrl to parseWebAppLearnersArray(arr, grade)
    }
  }

  suspend fun upsertLearnerViaWebApp(
    webAppUrl: String,
    record: LearnerProgressRecord,
    grade: AralGrade = record.activeGrade,
  ): CentralUpsertResult = withContext(Dispatchers.IO) {
    val payload = buildWebAppUpsertPayload(record, grade)
    val request = Request.Builder()
      .url(webAppUrl.trim())
      .post(payload.toString().toRequestBody(jsonMediaType))
      .build()

    client.newCall(request).execute().use { resp ->
      val text = resp.body?.string().orEmpty()
      if (!resp.isSuccessful) {
        throw IllegalStateException("Central Sheet save failed (HTTP ${resp.code}): ${extractApiError(text)}")
      }
      val json = parseWebAppJsonResponse(text)
      val rowNum = json.optInt("rowNumber", 0)
      if (rowNum < SheetCoordinateHelper.FIRST_LEARNER_ROW) {
        throw IllegalStateException("Central Sheet did not return a valid row number.")
      }
      val ssUrl = json.optString("spreadsheetUrl").takeIf { it.isNotBlank() }
      CentralUpsertResult(rowNumber = rowNum, spreadsheetUrl = ssUrl)
    }
  }

  private fun parseWebAppJsonResponse(body: String): JSONObject {
    val trimmed = body.trim()
    if (!trimmed.startsWith("{")) {
      throw IllegalStateException(
        "Apps Script returned non-JSON. Make sure Web App access is set to 'Anyone' and redeployed.",
      )
    }
    val json = JSONObject(trimmed)
    if (!json.optBoolean("ok", false)) {
      throw IllegalStateException(json.optString("error").ifBlank { "Apps Script returned an error." })
    }
    return json
  }

  companion object {
    fun isConfiguredWebAppUrl(raw: String?): Boolean {
      val trimmed = raw?.trim().orEmpty()
      if (trimmed.isBlank()) return false
      if (trimmed.contains("YOUR_APPS_SCRIPT_WEB_APP_URL", ignoreCase = true)) return false
      return (trimmed.startsWith("https://script.google.com/", ignoreCase = true) ||
        trimmed.startsWith("https://script.googleusercontent.com/", ignoreCase = true)) &&
        trimmed.length > 35
    }

    fun configuredWebAppUrl(): String? = null

    fun buildWebAppUpsertPayload(
      record: LearnerProgressRecord,
      grade: AralGrade,
    ): JSONObject {
      val maxActs = grade.sheetColumnSlots
      val gp = record.progressFor(grade)
      val actsObj = JSONObject()
      for ((actId, act) in gp.records) {
        if (actId !in 1..maxActs) continue
        actsObj.put(
          actId.toString(),
          JSONObject().apply {
            put("score", SheetCoordinateHelper.formatScoreForSheet(act.correctCount, act.totalItems))
            put("timestamp", act.timestamp)
          },
        )
      }
      return JSONObject().apply {
        put("action", "upsertLearner")
        put("grade", grade.name)
        put("name", record.profile.name)
        put("gradeLevel", record.profile.gradeLevel)
        put("school", record.profile.school)
        put("deviceAddress", record.profile.deviceAddress)
        put("sheetRowNumber", gp.sheetRowNumber ?: 0)
        put("resumePosition", "${gp.lastWorksheetId}:${gp.lastItemIndex}")
        put("completedSummary", "${record.completedCountFor(grade)}/${grade.totalWorksheets}")
        put("activities", actsObj)
      }
    }

    fun parseWebAppLearnersArray(
      arr: JSONArray,
      grade: AralGrade,
    ): List<LearnerProgressRecord> {
      val maxActs = grade.sheetColumnSlots
      val gradeCatalog = WorksheetCatalog.getWorksheets(grade).associateBy { it.id }
      val learners = mutableListOf<LearnerProgressRecord>()

      for (i in 0 until arr.length()) {
        val obj = arr.optJSONObject(i) ?: continue
        val name = obj.optString("name").trim()
        if (name.isBlank()) continue
        val rowNumber = obj.optInt("rowNumber", SheetCoordinateHelper.FIRST_LEARNER_ROW + i)
          .coerceAtLeast(SheetCoordinateHelper.FIRST_LEARNER_ROW)
        val gradeText = obj.optString("gradeLevel").trim().ifBlank { grade.shortLabel }
        val school = obj.optString("school").trim().ifBlank { "Bagacay Elementary School" }
        val deviceId = obj.optString("deviceAddress").trim()
        val lastWs = obj.optInt("lastWorksheetId", 1).coerceIn(1, maxActs)
        val lastItem = obj.optInt("lastItemIndex", 0).coerceAtLeast(0)

        val actsObj = obj.optJSONObject("activities")
        val activityMap = mutableMapOf<Int, ActivityRecord>()
        if (actsObj != null) {
          val keys = actsObj.keys()
          while (keys.hasNext()) {
            val k = keys.next()
            val actId = k.toIntOrNull() ?: continue
            if (actId !in 1..maxActs) continue
            val actJson = actsObj.optJSONObject(k) ?: continue
            val scoreRaw = actJson.optString("score").trim()
            if (scoreRaw.isBlank()) continue
            val fallbackTotal = gradeCatalog[actId]?.itemCount ?: 20
            val parsedScore = SheetCoordinateHelper.parseScoreFromSheet(scoreRaw, fallbackTotal) ?: continue
            val tsRaw = actJson.optString("timestamp").trim()
            val pct = SheetCoordinateHelper.computePercent(parsedScore.first, parsedScore.second)
            val stars = when {
              pct >= 90 -> 3
              pct >= 50 -> 2
              parsedScore.first > 0 -> 1
              else -> 0
            }
            activityMap[actId] = ActivityRecord(
              worksheetId = actId,
              correctCount = parsedScore.first,
              totalItems = parsedScore.second,
              stars = stars,
              timestamp = tsRaw,
              syncedToSheet = true,
              completedRound = true,
            )
          }
        }

        val gpState = GradeProgressState(
          records = activityMap,
          lastWorksheetId = lastWs,
          lastItemIndex = lastItem,
          pendingSheetSync = false,
          sheetRowNumber = rowNumber,
        )
        learners.add(
          LearnerProgressRecord(
            profile = LearnerProfile(
              name = name,
              gradeLevel = gradeText,
              school = school,
              deviceAddress = deviceId,
              sheetRowNumber = if (grade == AralGrade.GRADE_4) rowNumber else null,
            ),
            records = activityMap,
            lastWorksheetId = lastWs,
            lastItemIndex = lastItem,
            pendingSheetSync = false,
            activeGrade = grade,
            gradeProgress = mapOf(grade to gpState),
          ),
        )
      }
      return learners
    }
  }
}
