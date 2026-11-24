package com.example.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.model.ActivityRecord
import com.example.model.LearnerProfile
import com.example.model.LearnerProgressRecord
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

private val Context.recordsDataStore by preferencesDataStore(name = "aral_math_learner_records")

data class StoredCloudMeta(
  val spreadsheetId: String? = null,
  val spreadsheetUrl: String? = null,
  val connectedEmail: String? = null,
)

class LearnerRecordsStore(private val context: Context) {
  private val learnersJsonKey = stringPreferencesKey("learners_json_v1")
  private val activeLearnerKeyPref = stringPreferencesKey("active_learner_key")
  private val spreadsheetIdKey = stringPreferencesKey("sheet_id")
  private val spreadsheetUrlKey = stringPreferencesKey("sheet_url")
  private val connectedEmailKey = stringPreferencesKey("connected_email")
  private val fallbackUuidKey = stringPreferencesKey("fallback_device_uuid")

  val learnersFlow: Flow<Map<String, LearnerProgressRecord>> = context.recordsDataStore.data.map { prefs ->
    decodeLearnersMap(prefs[learnersJsonKey].orEmpty())
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
      val current = decodeLearnersMap(prefs[learnersJsonKey].orEmpty()).toMutableMap()
      current[record.profile.normalizedKey] = record
      prefs[learnersJsonKey] = encodeLearnersMap(current)
    }
  }

  suspend fun mergeFromCloud(cloudLearners: List<LearnerProgressRecord>, grade: com.example.model.AralGrade = com.example.model.AralGrade.GRADE_4) {
    if (cloudLearners.isEmpty()) return
    context.recordsDataStore.edit { prefs ->
      val local = decodeLearnersMap(prefs[learnersJsonKey].orEmpty()).toMutableMap()
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
            sheetRowNumber = if (grade == com.example.model.AralGrade.GRADE_4) {
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
      prefs[learnersJsonKey] = encodeLearnersMap(local)
    }
  }

  private fun encodeGradeProgress(gp: com.example.model.GradeProgressState): JSONObject {
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

  private fun decodeGradeProgress(obj: JSONObject, maxWorksheets: Int): com.example.model.GradeProgressState {
    val recordsMap = mutableMapOf<Int, ActivityRecord>()
    val recObj = obj.optJSONObject("records")
    if (recObj != null) {
      val wsKeys = recObj.keys()
      while (wsKeys.hasNext()) {
        val wsKey = wsKeys.next()
        val wsId = wsKey.toIntOrNull() ?: continue
        val act = recObj.optJSONObject(wsKey) ?: continue
        val correctCount = act.optInt("correctCount", 0)
        val totalItems = act.optInt("totalItems", 1)
        recordsMap[wsId] = ActivityRecord(
          worksheetId = act.optInt("worksheetId", wsId),
          correctCount = correctCount,
          totalItems = totalItems,
          stars = act.optInt("stars", 0),
          timestamp = act.optString("timestamp", ""),
          syncedToSheet = act.optBoolean("syncedToSheet", false),
          correctIndices = jsonArrayToIntSet(act.optJSONArray("correctIndices")),
          wrongIndices = jsonArrayToIntSet(act.optJSONArray("wrongIndices")),
          wrongAttemptCounts = decodeIntIntMap(act.optJSONObject("wrongAttemptCounts")),
          lastItemIndex = act.optInt("lastItemIndex", 0).coerceAtLeast(0),
          completedRound = act.optBoolean("completedRound", correctCount > 0 && act.optJSONArray("correctIndices") == null),
        )
      }
    }
    val inProgWs = if (obj.isNull("inProgressWorksheetId")) null else obj.optInt("inProgressWorksheetId")
    val rowNum = if (obj.isNull("sheetRowNumber")) null else obj.optInt("sheetRowNumber")
    val safeMax = maxWorksheets.coerceAtLeast(1)
    return com.example.model.GradeProgressState(
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

  private fun encodeLearnersMap(map: Map<String, LearnerProgressRecord>): String {
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
      for (g in com.example.model.AralGrade.entries) {
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

  private fun decodeLearnersMap(raw: String): Map<String, LearnerProgressRecord> {
    if (raw.isBlank()) return emptyMap()
    return runCatching {
      val root = JSONObject(raw)
      val out = linkedMapOf<String, LearnerProgressRecord>()
      val keys = root.keys()
      while (keys.hasNext()) {
        val key = keys.next()
        val obj = root.optJSONObject(key) ?: continue
        val p = obj.optJSONObject("profile") ?: continue
        val rowNum = if (p.isNull("sheetRowNumber")) null else p.optInt("sheetRowNumber")
        val profile = LearnerProfile(
          name = p.optString("name"),
          gradeLevel = p.optString("gradeLevel", "Grade 4"),
          school = p.optString("school", "Bagacay Elementary School"),
          deviceAddress = p.optString("deviceAddress"),
          sheetRowNumber = rowNum?.takeIf { it >= SheetCoordinateHelper.FIRST_LEARNER_ROW },
        )
        if (profile.name.isBlank()) continue

        val activeGrade = runCatching {
          com.example.model.AralGrade.valueOf(obj.optString("activeGrade", "GRADE_4"))
        }.getOrDefault(com.example.model.AralGrade.fromGradeLevelText(profile.gradeLevel))

        val gpObj = obj.optJSONObject("gradeProgress")
        val gpMap = mutableMapOf<com.example.model.AralGrade, com.example.model.GradeProgressState>()
        if (gpObj != null) {
          for (g in com.example.model.AralGrade.entries) {
            val gJson = gpObj.optJSONObject(g.name)
            if (gJson != null) {
              gpMap[g] = decodeGradeProgress(gJson, g.totalWorksheets)
            }
          }
        } else {
          // Legacy v1 Grade 4 JSON
          val g4State = decodeGradeProgress(obj, com.example.model.AralGrade.GRADE_4.totalWorksheets)
            .copy(sheetRowNumber = profile.sheetRowNumber)
          gpMap[com.example.model.AralGrade.GRADE_4] = g4State
        }

        val activeState = gpMap[activeGrade] ?: com.example.model.GradeProgressState()
        out[profile.normalizedKey] = LearnerProgressRecord(
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
      out
    }.getOrDefault(emptyMap())
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
