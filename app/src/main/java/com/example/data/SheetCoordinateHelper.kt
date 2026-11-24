package com.example.data

object SheetCoordinateHelper {
  const val TEACHER_EMAIL = "franz.padojinog@gmail.com"
  const val SPREADSHEET_TITLE = "ARAL MATH for BAGACAY ES - Learner Records"
  const val SHEET_TAB_NAME = "Grade 4"
  const val FIRST_LEARNER_ROW = 4
  // Legacy Grade 4 defaults (preserved for backward compatibility)
  const val TOTAL_ACTIVITIES = 34
  const val MASTERY_PERCENT_THRESHOLD = 90

  // Metadata columns after Activity 34 (BQ = index 68)
  const val COL_META_NAME = 69       // BR
  const val COL_META_GRADE = 70      // BS
  const val COL_META_SCHOOL = 71     // BT
  const val COL_META_DEVICE_ID = 72  // BU
  const val COL_META_RESUME = 73     // BV
  const val COL_META_COMPLETED = 74  // BW
  const val TOTAL_SHEET_COLUMNS = 75 // A..BW

  fun totalActivitiesFor(grade: com.example.model.AralGrade): Int = grade.sheetColumnSlots

  fun colMetaName(grade: com.example.model.AralGrade): Int = 1 + grade.sheetColumnSlots * 2
  fun colMetaGrade(grade: com.example.model.AralGrade): Int = colMetaName(grade) + 1
  fun colMetaSchool(grade: com.example.model.AralGrade): Int = colMetaName(grade) + 2
  fun colMetaDeviceId(grade: com.example.model.AralGrade): Int = colMetaName(grade) + 3
  fun colMetaResume(grade: com.example.model.AralGrade): Int = colMetaName(grade) + 4
  fun colMetaCompleted(grade: com.example.model.AralGrade): Int = colMetaName(grade) + 5
  fun totalColumnsFor(grade: com.example.model.AralGrade): Int = colMetaCompleted(grade) + 1
  fun lastColumnLetters(grade: com.example.model.AralGrade): String =
    columnIndexToLetters(totalColumnsFor(grade) - 1)

  fun columnIndexToLetters(index: Int): String {
    require(index >= 0) { "Column index must be non-negative" }
    val sb = StringBuilder()
    var current = index
    while (current >= 0) {
      val rem = current % 26
      sb.append(('A'.code + rem).toChar())
      current = (current / 26) - 1
    }
    return sb.reverse().toString()
  }

  /**
   * Activity 1 score -> index 1 (B), Activity 2 score -> index 3 (D), ...
   */
  fun activityScoreColumnIndex(
    activityId: Int,
    maxActivities: Int = TOTAL_ACTIVITIES,
  ): Int {
    require(activityId in 1..maxActivities) { "Activity id must be 1..$maxActivities" }
    return 1 + (activityId - 1) * 2
  }

  /**
   * Activity 1 timestamp -> index 2 (C), Activity 2 timestamp -> index 4 (E), ...
   */
  fun activityTimestampColumnIndex(
    activityId: Int,
    maxActivities: Int = TOTAL_ACTIVITIES,
  ): Int {
    require(activityId in 1..maxActivities) { "Activity id must be 1..$maxActivities" }
    return 2 + (activityId - 1) * 2
  }

  fun activityScoreCell(
    activityId: Int,
    rowNumber: Int,
    maxActivities: Int = TOTAL_ACTIVITIES,
  ): String {
    return "${columnIndexToLetters(activityScoreColumnIndex(activityId, maxActivities))}$rowNumber"
  }

  fun activityTimestampCell(
    activityId: Int,
    rowNumber: Int,
    maxActivities: Int = TOTAL_ACTIVITIES,
  ): String {
    return "${columnIndexToLetters(activityTimestampColumnIndex(activityId, maxActivities))}$rowNumber"
  }

  fun computePercent(correctCount: Int, totalItems: Int): Int {
    if (totalItems <= 0) return 0
    return ((correctCount.coerceIn(0, totalItems)) * 100) / totalItems
  }

  fun hasPassedMastery(correctCount: Int, totalItems: Int): Boolean {
    if (totalItems <= 0) return false
    return (correctCount * 100) >= (totalItems * MASTERY_PERCENT_THRESHOLD)
  }

  fun requiredCorrectForMastery(totalItems: Int): Int {
    if (totalItems <= 0) return 0
    return ((totalItems * MASTERY_PERCENT_THRESHOLD) + 99) / 100
  }

  fun formatScoreForSheet(correctCount: Int, totalItems: Int): String {
    val pct = computePercent(correctCount, totalItems)
    return "$pct% ($correctCount/$totalItems)"
  }

  fun parseScoreFromSheet(raw: String, fallbackTotalItems: Int): Pair<Int, Int>? {
    val trimmed = raw.trim()
    if (trimmed.isEmpty()) return null
    val fractionMatch = Regex("""(\d+)\s*/\s*(\d+)""").find(trimmed)
    if (fractionMatch != null) {
      val correct = fractionMatch.groupValues[1].toIntOrNull() ?: return null
      val total = fractionMatch.groupValues[2].toIntOrNull()?.coerceAtLeast(1) ?: return null
      return correct.coerceIn(0, total) to total
    }
    val pctMatch = Regex("""(\d+)\s*%""").find(trimmed)
    if (pctMatch != null && fallbackTotalItems > 0) {
      val pct = pctMatch.groupValues[1].toIntOrNull()?.coerceIn(0, 100) ?: return null
      val approxCorrect = ((pct * fallbackTotalItems) + 50) / 100
      return approxCorrect.coerceIn(0, fallbackTotalItems) to fallbackTotalItems
    }
    return null
  }

  fun normalizeLearnerName(name: String): String {
    return name.trim().replace(Regex("\\s+"), " ").lowercase()
  }

  fun buildHeaderRows(grade: com.example.model.AralGrade = com.example.model.AralGrade.GRADE_4): List<List<String>> {
    val totalCols = totalColumnsFor(grade)
    val maxActs = grade.sheetColumnSlots
    val row1 = MutableList(totalCols) { "" }
    row1[0] = "ARAL MATH for BAGACAY ES — ${grade.shortLabel} Learner Progress & Mastery Tracker"

    val row2 = MutableList(totalCols) { "" }
    row2[0] = "Teacher Target: $TEACHER_EMAIL | Rule: >= 90% required to pass | 100% = Perfect ★"

    val row3 = MutableList(totalCols) { "" }
    row3[0] = "Learner Profile (Name | Grade | School | Device Address)"
    for (act in 1..maxActs) {
      row3[activityScoreColumnIndex(act, maxActs)] = "Act $act Score"
      row3[activityTimestampColumnIndex(act, maxActs)] = "Act $act Date & Time"
    }
    row3[colMetaName(grade)] = "Learner Name"
    row3[colMetaGrade(grade)] = "Grade Level"
    row3[colMetaSchool(grade)] = "School"
    row3[colMetaDeviceId(grade)] = "CP / Device Address"
    row3[colMetaResume(grade)] = "Resume Position (Act:Item)"
    row3[colMetaCompleted(grade)] = "Completed (>=90%)"

    return listOf(row1, row2, row3)
  }
}
