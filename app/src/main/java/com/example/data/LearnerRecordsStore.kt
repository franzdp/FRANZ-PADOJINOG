package com.example.data

import android.content.Context
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.ActivityRecord
import com.example.model.AralGrade
import com.example.model.GradeProgressState
import com.example.model.LearnerProfile
import com.example.model.LearnerProgressRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

private val Context.recordsDataStore by preferencesDataStore(name = "aral_math_learner_records")

data class StoredCloudMeta(
  val spreadsheetId: String? = null,
  val spreadsheetUrl: String? = null,
  val connectedEmail: String? = null,
)

data class ImportBackupResult(
  val importedLearners: Int,
  val totalActivitiesMerged: Int,
)

data class ParsedBackupPayload(
  val learners: Map<String, LearnerProgressRecord>,
  val activeLearnerKey: String?,
)

class LearnerRecordsStore(private val context: Context) {
  private val learnersJsonKey = stringPreferencesKey("learners_json_v1")
  private val learnersBackupJsonKey = stringPreferencesKey("learners_json_backup_v1")
  private val corruptSnapshotKey = stringPreferencesKey("learners_json_corrupt_snapshot")
  private val activeLearnerKeyPref = stringPreferencesKey("active_learner_key")
  private val spreadsheetIdKey = stringPreferencesKey("sheet_id")
  private val spreadsheetUrlKey = stringPreferencesKey("sheet_url")
  private val connectedEmailKey = stringPreferencesKey("connected_email")
  private val fallbackUuidKey = stringPreferencesKey("fallback_device_uuid")

  val learnersFlow: Flow<Map<String, LearnerProgressRecord>> = context.recordsDataStore.data.map { prefs ->
    decodeWithBackupRecovery(prefs)
  }

  val activeLearnerKeyFlow: Flow<String?> = context.recordsDataStore.data.map { prefs ->
    prefs[activeLearnerKeyPref]?.takeIf { it.isNotBlank() }
  }

  val cloudMetaFlow: Flow<StoredCloudMeta> = context.recordsDataStore.data.map { prefs ->
    StoredCloudMeta(
      spreadsheetId = prefs[spreadsheetIdKey]?.takeIf { it.isNotBlank() },
      spreadsheetUrl = prefs[spreadsheetUrlKey]?.takeIf { it.isNotBlank() },
      connectedEmail = prefs[connectedEmailKey]?.takeIf { it.isNotBlank() },
    )
  }

  suspend fun getOrCreateFallbackUuid(): String {
    val existing = context.recordsDataStore.data.first()[fallbackUuidKey]
    if (!existing.isNullOrBlank()) return existing
    val created = UUID.randomUUID().toString()
    context.recordsDataStore.edit { it[fallbackUuidKey] = created }
    return created
  }

  suspend fun saveCloudMeta(spreadsheetId: String?, spreadsheetUrl: String?, connectedEmail: String?) {
    context.recordsDataStore.edit { prefs ->
      if (spreadsheetId != null) prefs[spreadsheetIdKey] = spreadsheetId else prefs.remove(spreadsheetIdKey)
      if (spreadsheetUrl != null) prefs[spreadsheetUrlKey] = spreadsheetUrl else prefs.remove(spreadsheetUrlKey)
      if (connectedEmail != null) prefs[connectedEmailKey] = connectedEmail else prefs.remove(connectedEmailKey)
    }
  }

  suspend fun setActiveLearnerKey(key: String?) {
    context.recordsDataStore.edit { prefs ->
      if (key.isNullOrBlank()) prefs.remove(activeLearnerKeyPref) else prefs[activeLearnerKeyPref] = key
    }
  }

  suspend fun upsertLearner(record: LearnerProgressRecord) {
    context.recordsDataStore.edit { prefs ->
      val current = readMutableSafeForEdit(prefs)
      val existing = current[record.profile.normalizedKey]
      current[record.profile.normalizedKey] = if (existing != null && record.gradeProgress.isEmpty() && existing.gradeProgress.isNotEmpty()) {
        mergeTwoLearnerRecords(existing, record)
      } else {
        record
      }
      persistLearnersMap(prefs, current)
    }
  }

  suspend fun exportBackupJson(): String {
    val prefs = context.recordsDataStore.data.first()
    val current = decodeWithBackupRecovery(prefs)
    val activeKey = prefs[activeLearnerKeyPref]?.takeIf { it.isNotBlank() }
    return buildBackupJson(current, activeKey)
  }

  suspend fun importBackupJson(raw: String): ImportBackupResult {
    val parsed = parseBackupPayload(raw)
    var mergedActivitiesCount = 0

    context.recordsDataStore.edit { prefs ->
      val current = readMutableSafeForEdit(prefs)
      for ((key, incoming) in parsed.learners) {
        val existing = current[key]
        val merged = if (existing == null) incoming else mergeTwoLearnerRecords(existing, incoming)
        current[key] = merged
        mergedActivitiesCount += incoming.gradeProgress.values.sumOf { it.records.size }
      }
      persistLearnersMap(prefs, current)
      val currentActive = prefs[activeLearnerKeyPref]?.takeIf { it.isNotBlank() }
      if (currentActive == null) {
        val candidateKey = parsed.activeLearnerKey?.takeIf { current.containsKey(it) } ?: parsed.learners.keys.firstOrNull()
        if (candidateKey != null) {
          prefs[activeLearnerKeyPref] = candidateKey
        }
      }
    }

    return ImportBackupResult(
      importedLearners = parsed.learners.size,
      totalActivitiesMerged = mergedActivitiesCount,
    )
  }

  suspend fun mergeFromCloud(cloudLearners: List<LearnerProgressRecord>, grade: AralGrade = AralGrade.GRADE_4) {
    if (cloudLearners.isEmpty()) return
    context.recordsDataStore.edit { prefs ->
      val local = readMutableSafeForEdit(prefs)
      for (cloud in cloudLearners) {
        val key = cloud.profile.normalizedKey
        val cloudGp = cloud.progressFor(grade)
        val existing = local[key]
        if (existing == null) {
          local[key] = cloud.switchActiveGrade(grade)
        } else {
          val localGp = existing.progressFor(grade)
          val mergedRecords = localGp.records.toMutableMap()
          for ((wsId, cloudAct) in cloudGp.records) {
            val localAct = mergedRecords[wsId]
            if (localAct == null) {
              mergedRecords[wsId] = cloudAct
            } else if (cloudAct.percent > localAct.percent ||
              (cloudAct.percent == localAct.percent && cloudAct.correctCount >= localAct.correctCount && !localGp.pendingSheetSync)
            ) {
              mergedRecords[wsId] = cloudAct.copy(
                correctIndices = if (localAct.correctIndices.size == cloudAct.correctCount) localAct.correctIndices else cloudAct.correctIndices,
                wrongIndices = if (localAct.correctIndices.size == cloudAct.correctCount) localAct.wrongIndices else cloudAct.wrongIndices,
                wrongAttemptCounts = mergeWrongCounts(localAct.wrongAttemptCounts, cloudAct.wrongAttemptCounts),
                lastItemIndex = localAct.lastItemIndex,
                completedRound = localAct.completedRound || cloudAct.completedRound,
              )
            }
          }
          val useCloudResume = !localGp.pendingSheetSync && localGp.inProgressWorksheetId == null
          val mergedGp = localGp.copy(
            records = mergedRecords,
            lastWorksheetId = if (useCloudResume) cloudGp.lastWorksheetId else localGp.lastWorksheetId,
            lastItemIndex = if (useCloudResume) cloudGp.lastItemIndex else localGp.lastItemIndex,
            sheetRowNumber = cloudGp.sheetRowNumber ?: localGp.sheetRowNumber,
          )
          val updatedProfile = existing.profile.copy(
            sheetRowNumber = if (grade == AralGrade.GRADE_4) {
              mergedGp.sheetRowNumber ?: existing.profile.sheetRowNumber
            } else {
              existing.profile.sheetRowNumber
            },
            gradeLevel = existing.profile.gradeLevel.ifBlank { cloud.profile.gradeLevel },
            school = existing.profile.school.ifBlank { cloud.profile.school },
            deviceAddress = existing.profile.deviceAddress.ifBlank { cloud.profile.deviceAddress },
          )
          local[key] = existing.copy(profile = updatedProfile).withGradeProgress(grade, mergedGp)
        }
      }
      persistLearnersMap(prefs, local)
    }
  }

  private fun readMutableSafeForEdit(prefs: MutablePreferences): MutableMap<String, LearnerProgressRecord> {
    val primaryRaw = prefs[learnersJsonKey].orEmpty()
    val primaryDecoded = decodeLearnersMap(primaryRaw)
    if (primaryDecoded.isNotEmpty()) {
      return primaryDecoded.toMutableMap()
    }
    if (primaryRaw.isNotBlank() && primaryRaw.trim() != "{}") {
      prefs[corruptSnapshotKey] = primaryRaw
    }
    val backupRaw = prefs[learnersBackupJsonKey].orEmpty()
    return decodeLearnersMap(backupRaw).toMutableMap()
  }

  private fun persistLearnersMap(prefs: MutablePreferences, map: Map<String, LearnerProgressRecord>) {
    val encoded = encodeLearnersMap(map)
    prefs[learnersJsonKey] = encoded
    if (map.isNotEmpty()) {
      prefs[learnersBackupJsonKey] = encoded
    }
  }

  private fun decodeWithBackupRecovery(prefs: Preferences): Map<String, LearnerProgressRecord> {
    val primaryRaw = prefs[learnersJsonKey].orEmpty()
    val primaryDecoded = decodeLearnersMap(primaryRaw)
    if (primaryDecoded.isNotEmpty() || primaryRaw.isBlank() || primaryRaw.trim() == "{}") {
      return primaryDecoded
    }
    val backupRaw = prefs[learnersBackupJsonKey].orEmpty()
    return decodeLearnersMap(backupRaw)
  }

  companion object {
    const val BACKUP_FORMAT_V1 = "aral_math_backup_v1"

    fun buildBackupJson(map: Map<String, LearnerProgressRecord>, activeKey: String?): String {
      val learnersJson = JSONObject(encodeLearnersMap(map))
      return JSONObject().apply {
        put("format", BACKUP_FORMAT_V1)
        put("exportedAt", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date()))
        if (!activeKey.isNullOrBlank()) put("activeLearnerKey", activeKey)
        put("learners", learnersJson)
      }.toString(2)
    }

    fun parseBackupPayload(raw: String): ParsedBackupPayload {
      val trimmed = raw.trim()
      require(trimmed.isNotEmpty()) { "Backup file is empty." }

      val root = runCatching { JSONObject(trimmed) }.getOrElse {
        throw IllegalArgumentException("Invalid backup file format.")
      }

      val learnersObj = when {
        root.has("learners") && root.optJSONObject("learners") != null -> root.getJSONObject("learners")
        else -> root
      }

      val importedMap = decodeLearnersMap(learnersObj.toString())
      require(importedMap.isNotEmpty()) { "No learner records were found in that backup file." }

      val importedActiveKey = root.optString("activeLearnerKey").takeIf { it.isNotBlank() }
      return ParsedBackupPayload(learners = importedMap, activeLearnerKey = importedActiveKey)
    }

    fun mergeTwoLearnerRecords(
      local: LearnerProgressRecord,
      incoming: LearnerProgressRecord,
    ): LearnerProgressRecord {
      var result = local.copy(
        profile = local.profile.copy(
          name = local.profile.name.ifBlank { incoming.profile.name },
          gradeLevel = local.profile.gradeLevel.ifBlank { incoming.profile.gradeLevel },
          school = local.profile.school.ifBlank { incoming.profile.school },
          deviceAddress = local.profile.deviceAddress.ifBlank { incoming.profile.deviceAddress },
          sheetRowNumber = local.profile.sheetRowNumber ?: incoming.profile.sheetRowNumber,
        ),
      )

      for (grade in AralGrade.entries) {
        val a = local.progressFor(grade)
        val b = incoming.progressFor(grade)
        val bHasAnything = b.records.isNotEmpty() || b.inProgressWorksheetId != null || b.lastWorksheetId > 1 || b.lastItemIndex > 0 || b.sheetRowNumber != null
        val aHasAnything = a.records.isNotEmpty() || a.inProgressWorksheetId != null || a.lastWorksheetId > 1 || a.lastItemIndex > 0 || a.sheetRowNumber != null
        if (!bHasAnything && !aHasAnything) continue

        val mergedRecords = a.records.toMutableMap()
        for ((wsId, recB) in b.records) {
          val recA = mergedRecords[wsId]
          mergedRecords[wsId] = if (recA == null) {
            recB
          } else {
            mergeActivityRecord(recA, recB)
          }
        }

        val pickBResume = a.records.isEmpty() && a.inProgressWorksheetId == null
        val mergedGp = a.copy(
          records = mergedRecords,
          lastWorksheetId = if (pickBResume) b.lastWorksheetId else a.lastWorksheetId,
          lastItemIndex = if (pickBResume) b.lastItemIndex else a.lastItemIndex,
          inProgressWorksheetId = a.inProgressWorksheetId ?: b.inProgressWorksheetId,
          inProgressCorrectIndices = if (a.inProgressWorksheetId == b.inProgressWorksheetId) {
            a.inProgressCorrectIndices + b.inProgressCorrectIndices
          } else if (a.inProgressWorksheetId != null) {
            a.inProgressCorrectIndices
          } else {
            b.inProgressCorrectIndices
          },
          inProgressWrongIndices = if (a.inProgressWorksheetId == b.inProgressWorksheetId) {
            (a.inProgressWrongIndices + b.inProgressWrongIndices) - (a.inProgressCorrectIndices + b.inProgressCorrectIndices)
          } else if (a.inProgressWorksheetId != null) {
            a.inProgressWrongIndices
          } else {
            b.inProgressWrongIndices
          },
          inProgressWrongAttemptCounts = if (a.inProgressWorksheetId == b.inProgressWorksheetId) {
            mergeWrongCounts(a.inProgressWrongAttemptCounts, b.inProgressWrongAttemptCounts)
          } else if (a.inProgressWorksheetId != null) {
            a.inProgressWrongAttemptCounts
          } else {
            b.inProgressWrongAttemptCounts
          },
          pendingSheetSync = a.pendingSheetSync || b.pendingSheetSync,
          sheetRowNumber = a.sheetRowNumber ?: b.sheetRowNumber,
        )
        result = result.withGradeProgress(grade, mergedGp)
      }

      return result
    }

    private fun mergeActivityRecord(a: ActivityRecord, b: ActivityRecord): ActivityRecord {
      val unionCorrect = a.correctIndices + b.correctIndices
      val bestCorrectCount = maxOf(a.correctCount, b.correctCount, unionCorrect.size)
      val totalItems = maxOf(a.totalItems, b.totalItems, bestCorrectCount)
      val unionWrong = (a.wrongIndices + b.wrongIndices) - unionCorrect
      val mergedWrongCounts = mergeWrongCounts(a.wrongAttemptCounts, b.wrongAttemptCounts)
      val pickB = b.correctCount > a.correctCount || (b.correctCount == a.correctCount && b.stars >= a.stars && b.timestamp > a.timestamp)
      return ActivityRecord(
        worksheetId = a.worksheetId,
        correctCount = bestCorrectCount.coerceAtMost(totalItems),
        totalItems = totalItems,
        stars = maxOf(a.stars, b.stars),
        timestamp = if (pickB && b.timestamp.isNotBlank()) b.timestamp else a.timestamp.ifBlank { b.timestamp },
        syncedToSheet = a.syncedToSheet && b.syncedToSheet,
        correctIndices = unionCorrect,
        wrongIndices = unionWrong,
        wrongAttemptCounts = mergedWrongCounts,
        lastItemIndex = if (pickB) b.lastItemIndex else a.lastItemIndex,
        completedRound = a.completedRound || b.completedRound,
      )
    }

    private fun mergeWrongCounts(a: Map<Int, Int>, b: Map<Int, Int>): Map<Int, Int> {
      if (a.isEmpty()) return b
      if (b.isEmpty()) return a
      val merged = a.toMutableMap()
      for ((k, v) in b) {
        merged[k] = maxOf(merged[k] ?: 0, v)
      }
      return merged
    }

    private fun encodeGradeProgress(gp: GradeProgressState): JSONObject {
      return JSONObject().apply {
        put("lastWorksheetId", gp.lastWorksheetId)
        put("lastItemIndex", gp.lastItemIndex)
        put("inProgressWorksheetId", gp.inProgressWorksheetId ?: JSONObject.NULL)
        put("inProgressCorrectIndices", JSONArray(gp.inProgressCorrectIndices.toList()))
        put("inProgressWrongIndices", JSONArray(gp.inProgressWrongIndices.toList()))
        put("inProgressWrongAttemptCounts", encodeIntIntMap(gp.inProgressWrongAttemptCounts))
        put("pendingSheetSync", gp.pendingSheetSync)
        put("sheetRowNumber", gp.sheetRowNumber ?: JSONObject.NULL)
        val recordsObj = JSONObject()
        for ((wsId, act) in gp.records) {
          val actObj = JSONObject().apply {
            put("worksheetId", act.worksheetId)
            put("correctCount", act.correctCount)
            put("totalItems", act.totalItems)
            put("stars", act.stars)
            put("timestamp", act.timestamp)
            put("syncedToSheet", act.syncedToSheet)
            put("correctIndices", JSONArray(act.correctIndices.toList()))
            put("wrongIndices", JSONArray(act.wrongIndices.toList()))
            put("wrongAttemptCounts", encodeIntIntMap(act.wrongAttemptCounts))
            put("lastItemIndex", act.lastItemIndex)
            put("completedRound", act.completedRound)
          }
          recordsObj.put(wsId.toString(), actObj)
        }
        put("records", recordsObj)
      }
    }

    private fun decodeGradeProgress(obj: JSONObject, maxWorksheets: Int): GradeProgressState {
      val recordsMap = mutableMapOf<Int, ActivityRecord>()
      val recObj = obj.optJSONObject("records")
      if (recObj != null) {
        val wsKeys = recObj.keys()
        while (wsKeys.hasNext()) {
          val wsKey = wsKeys.next()
          val wsId = wsKey.toIntOrNull() ?: continue
          val act = recObj.optJSONObject(wsKey) ?: continue
          val correctCount = act.optInt("correctCount", 0).coerceAtLeast(0)
          val totalItems = act.optInt("totalItems", 1).coerceAtLeast(1)
          val correctSet = jsonArrayToIntSet(act.optJSONArray("correctIndices"))
          val wrongSet = jsonArrayToIntSet(act.optJSONArray("wrongIndices"))
          recordsMap[wsId] = ActivityRecord(
            worksheetId = act.optInt("worksheetId", wsId),
            correctCount = maxOf(correctCount, correctSet.size).coerceAtMost(totalItems),
            totalItems = totalItems,
            stars = act.optInt("stars", 0).coerceIn(0, 3),
            timestamp = act.optString("timestamp", ""),
            syncedToSheet = act.optBoolean("syncedToSheet", false),
            correctIndices = correctSet,
            wrongIndices = wrongSet,
            wrongAttemptCounts = decodeIntIntMap(act.optJSONObject("wrongAttemptCounts")),
            lastItemIndex = act.optInt("lastItemIndex", 0).coerceAtLeast(0),
            completedRound = act.optBoolean("completedRound", correctCount > 0 && act.optJSONArray("correctIndices") == null),
          )
        }
      }
      val inProgWs = if (obj.isNull("inProgressWorksheetId")) null else obj.optInt("inProgressWorksheetId", -1).takeIf { it > 0 }
      val rowNum = if (obj.isNull("sheetRowNumber")) null else obj.optInt("sheetRowNumber", -1).takeIf { it > 0 }
      val safeMax = maxWorksheets.coerceAtLeast(1)
      return GradeProgressState(
        records = recordsMap,
        lastWorksheetId = obj.optInt("lastWorksheetId", 1).coerceIn(1, safeMax),
        lastItemIndex = obj.optInt("lastItemIndex", 0).coerceAtLeast(0),
        inProgressWorksheetId = inProgWs,
        inProgressCorrectIndices = jsonArrayToIntSet(obj.optJSONArray("inProgressCorrectIndices")),
        inProgressWrongIndices = jsonArrayToIntSet(obj.optJSONArray("inProgressWrongIndices")),
        inProgressWrongAttemptCounts = decodeIntIntMap(obj.optJSONObject("inProgressWrongAttemptCounts")),
        pendingSheetSync = obj.optBoolean("pendingSheetSync", false),
        sheetRowNumber = rowNum?.takeIf { it >= SheetCoordinateHelper.FIRST_LEARNER_ROW },
      )
    }

    fun encodeLearnersMap(map: Map<String, LearnerProgressRecord>): String {
      val root = JSONObject()
      for ((key, record) in map) {
        val obj = JSONObject()
        val profileObj = JSONObject().apply {
          put("name", record.profile.name)
          put("gradeLevel", record.profile.gradeLevel)
          put("school", record.profile.school)
          put("deviceAddress", record.profile.deviceAddress)
          put("sheetRowNumber", record.profile.sheetRowNumber ?: JSONObject.NULL)
        }
        obj.put("profile", profileObj)
        obj.put("activeGrade", record.activeGrade.name)

        // Keep top-level active/Grade4 fields for clean serialization
        val activeState = record.progressFor(record.activeGrade)
        val legacyEncoded = encodeGradeProgress(activeState)
        val itKeys = legacyEncoded.keys()
        while (itKeys.hasNext()) {
          val k = itKeys.next()
          if (k != "sheetRowNumber") obj.put(k, legacyEncoded.get(k))
        }

        val gpMapObj = JSONObject()
        for (g in AralGrade.entries) {
          val gp = record.progressFor(g)
          if (gp.records.isNotEmpty() || gp.inProgressWorksheetId != null || gp.lastWorksheetId > 1 || gp.lastItemIndex > 0 || gp.sheetRowNumber != null || g == record.activeGrade) {
            gpMapObj.put(g.name, encodeGradeProgress(gp))
          }
        }
        obj.put("gradeProgress", gpMapObj)
        root.put(key, obj)
      }
      return root.toString()
    }

    fun decodeLearnersMap(raw: String): Map<String, LearnerProgressRecord> {
      if (raw.isBlank()) return emptyMap()
      val root = runCatching { JSONObject(raw) }.getOrNull() ?: return emptyMap()
      val out = linkedMapOf<String, LearnerProgressRecord>()
      val keys = root.keys()
      while (keys.hasNext()) {
        val key = keys.next()
        val parsed = runCatching { decodeSingleLearner(key, root.optJSONObject(key)) }.getOrNull() ?: continue
        val normKey = parsed.profile.normalizedKey
        val existing = out[normKey]
        out[normKey] = if (existing == null) parsed else mergeTwoLearnerRecords(existing, parsed)
      }
      return out
    }

    private fun decodeSingleLearner(fallbackKey: String, obj: JSONObject?): LearnerProgressRecord? {
      if (obj == null) return null
      val p = obj.optJSONObject("profile")
      val rawName = p?.optString("name")?.takeIf { it.isNotBlank() }
        ?: obj.optString("name").takeIf { it.isNotBlank() }
        ?: fallbackKey.takeIf { it.isNotBlank() }
        ?: return null
      val rowNum = when {
        p == null || p.isNull("sheetRowNumber") -> null
        else -> p.optInt("sheetRowNumber", -1).takeIf { it > 0 }
      }
      val profile = LearnerProfile(
        name = rawName,
        gradeLevel = p?.optString("gradeLevel", "Grade 4")?.ifBlank { "Grade 4" } ?: "Grade 4",
        school = p?.optString("school", "Bagacay Elementary School")?.ifBlank { "Bagacay Elementary School" } ?: "Bagacay Elementary School",
        deviceAddress = p?.optString("deviceAddress").orEmpty(),
        sheetRowNumber = rowNum?.takeIf { it >= SheetCoordinateHelper.FIRST_LEARNER_ROW },
      )
      if (profile.name.isBlank()) return null

      val gpObj = obj.optJSONObject("gradeProgress")
      val gpMap = mutableMapOf<AralGrade, GradeProgressState>()
      if (gpObj != null) {
        for (g in AralGrade.entries) {
          val gJson = gpObj.optJSONObject(g.name)
          if (gJson != null) {
            gpMap[g] = decodeGradeProgress(gJson, g.totalWorksheets)
          }
        }
      }

      val rawActiveGradeName = obj.optString("activeGrade", "")
      val parsedActiveGrade = runCatching {
        if (rawActiveGradeName.isNotBlank()) AralGrade.valueOf(rawActiveGradeName) else null
      }.getOrNull()

      val activeGrade = parsedActiveGrade
        ?: gpMap.entries.firstOrNull { (_, gp) -> gp.records.isNotEmpty() || gp.inProgressWorksheetId != null }?.key
        ?: AralGrade.fromGradeLevelText(profile.gradeLevel)

      val hasTopLevelRecords = (obj.optJSONObject("records")?.length() ?: 0) > 0 || !obj.isNull("inProgressWorksheetId")
      if (gpObj == null || (hasTopLevelRecords && (gpMap[activeGrade]?.records?.isEmpty() != false) && gpMap.values.all { it.records.isEmpty() })) {
        val fallbackGrade = if (rawActiveGradeName.isBlank()) AralGrade.GRADE_4 else activeGrade
        val legacyState = decodeGradeProgress(obj, fallbackGrade.totalWorksheets)
        val adjustedState = if (fallbackGrade == AralGrade.GRADE_4 && legacyState.sheetRowNumber == null) {
          legacyState.copy(sheetRowNumber = profile.sheetRowNumber)
        } else {
          legacyState
        }
        gpMap[fallbackGrade] = adjustedState
      }

      val activeState = gpMap[activeGrade] ?: GradeProgressState()
      return LearnerProgressRecord(
        profile = profile,
        records = activeState.records,
        lastWorksheetId = activeState.lastWorksheetId,
        lastItemIndex = activeState.lastItemIndex,
        inProgressWorksheetId = activeState.inProgressWorksheetId,
        inProgressCorrectIndices = activeState.inProgressCorrectIndices,
        inProgressWrongIndices = activeState.inProgressWrongIndices,
        inProgressWrongAttemptCounts = activeState.inProgressWrongAttemptCounts,
        pendingSheetSync = gpMap.values.any { it.pendingSheetSync },
        activeGrade = activeGrade,
        gradeProgress = gpMap,
      )
    }

    private fun jsonArrayToIntSet(arr: JSONArray?): Set<Int> {
      if (arr == null) return emptySet()
      val set = linkedSetOf<Int>()
      for (i in 0 until arr.length()) {
        set.add(arr.optInt(i))
      }
      return set
    }

    private fun encodeIntIntMap(map: Map<Int, Int>): JSONObject {
      val obj = JSONObject()
      for ((k, v) in map) {
        if (v > 0) obj.put(k.toString(), v)
      }
      return obj
    }

    private fun decodeIntIntMap(obj: JSONObject?): Map<Int, Int> {
      if (obj == null) return emptyMap()
      val out = linkedMapOf<Int, Int>()
      val keys = obj.keys()
      while (keys.hasNext()) {
        val k = keys.next()
        val intKey = k.toIntOrNull() ?: continue
        val count = obj.optInt(k, 0)
        if (count > 0) out[intKey] = count
      }
      return out
    }
  }
}
