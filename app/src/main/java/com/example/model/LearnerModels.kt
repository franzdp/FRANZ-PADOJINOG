package com.example.model

import com.example.data.SheetCoordinateHelper

enum class ActivityMasteryStatus(val label: String) {
  NOT_STARTED("Not Started"),
  NOT_COMPLETED("Not Completed"),
  NOT_PASSED("Not Passed"),
  PASSED("Passed"),
  PERFECT("Perfect Score"),
}

data class LearnerProfile(
  val name: String,
  val gradeLevel: String,
  val school: String,
  val deviceAddress: String,
  val sheetRowNumber: Int? = null,
) {
  val normalizedKey: String get() = SheetCoordinateHelper.normalizeLearnerName(name)
}

data class ActivityRecord(
  val worksheetId: Int,
  val correctCount: Int,
  val totalItems: Int,
  val stars: Int,
  val timestamp: String,
  val syncedToSheet: Boolean = false,
  val correctIndices: Set<Int> = emptySet(),
  val wrongIndices: Set<Int> = emptySet(),
  val wrongAttemptCounts: Map<Int, Int> = emptyMap(),
  val lastItemIndex: Int = 0,
  val completedRound: Boolean = false,
) {
  val percent: Int get() = SheetCoordinateHelper.computePercent(correctCount, totalItems)
  val isPassed90: Boolean get() = SheetCoordinateHelper.hasPassedMastery(correctCount, totalItems)
  val isPerfect100: Boolean get() = totalItems > 0 && correctCount >= totalItems

  val pendingWrongIndices: Set<Int>
    get() = wrongIndices - correctIndices

  fun wrongAttemptsFor(index: Int): Int {
    val recorded = wrongAttemptCounts[index] ?: 0
    return if (recorded > 0) recorded else if (index in wrongIndices) 1 else 0
  }

  /**
   * Items where the learner had difficulty:
   * tried more than once (>= 2 wrong attempts) or still unresolved.
   */
  val difficultyItemIndices: List<Int>
    get() = (pendingWrongIndices + wrongAttemptCounts.filterValues { it >= 2 }.keys).sorted()

  /**
   * Items tried more than once (>= 2 wrong attempts) and still wrong.
   */
  val repeatedStillWrongIndices: List<Int>
    get() = pendingWrongIndices.filter { wrongAttemptsFor(it) >= 2 }.sorted()

  val attemptedCount: Int
    get() = maxOf((correctIndices + wrongIndices).size, correctCount)

  val masteryStatus: ActivityMasteryStatus
    get() = when {
      isPerfect100 -> ActivityMasteryStatus.PERFECT
      isPassed90 -> ActivityMasteryStatus.PASSED
      attemptedCount == 0 && !completedRound -> ActivityMasteryStatus.NOT_STARTED
      completedRound || attemptedCount >= totalItems -> ActivityMasteryStatus.NOT_PASSED
      else -> ActivityMasteryStatus.NOT_COMPLETED
    }

  /**
   * Redirects the learner to where they left off or to the first item with a wrong answer.
   */
  fun recommendedStartIndex(): Int {
    if (totalItems <= 0) return 0
    val safeLast = lastItemIndex.coerceIn(0, totalItems - 1)
    if (!completedRound && safeLast !in correctIndices && safeLast !in wrongIndices) {
      return safeLast
    }
    val pendingWrong = pendingWrongIndices
    if (pendingWrong.isNotEmpty()) {
      return pendingWrong.min().coerceIn(0, totalItems - 1)
    }
    val firstUnanswered = (0 until totalItems).firstOrNull { it !in correctIndices }
    if (firstUnanswered != null) {
      return firstUnanswered
    }
    return safeLast
  }
}

data class GradeProgressState(
  val records: Map<Int, ActivityRecord> = emptyMap(),
  val lastWorksheetId: Int = 1,
  val lastItemIndex: Int = 0,
  val inProgressWorksheetId: Int? = null,
  val inProgressCorrectIndices: Set<Int> = emptySet(),
  val inProgressWrongIndices: Set<Int> = emptySet(),
  val inProgressWrongAttemptCounts: Map<Int, Int> = emptyMap(),
  val pendingSheetSync: Boolean = false,
  val sheetRowNumber: Int? = null,
)

data class LearnerProgressRecord(
  val profile: LearnerProfile,
  val records: Map<Int, ActivityRecord> = emptyMap(),
  val lastWorksheetId: Int = 1,
  val lastItemIndex: Int = 0,
  val inProgressWorksheetId: Int? = null,
  val inProgressCorrectIndices: Set<Int> = emptySet(),
  val inProgressWrongIndices: Set<Int> = emptySet(),
  val inProgressWrongAttemptCounts: Map<Int, Int> = emptyMap(),
  val pendingSheetSync: Boolean = false,
  val activeGrade: AralGrade = AralGrade.GRADE_4,
  val gradeProgress: Map<AralGrade, GradeProgressState> = emptyMap(),
) {
  fun progressFor(grade: AralGrade = activeGrade): GradeProgressState {
    val explicit = gradeProgress[grade]
    if (explicit != null) return explicit
    if (grade == AralGrade.GRADE_4 && (records.isNotEmpty() || inProgressWorksheetId != null || lastWorksheetId > 1 || lastItemIndex > 0 || profile.sheetRowNumber != null)) {
      return GradeProgressState(
        records = records,
        lastWorksheetId = lastWorksheetId,
        lastItemIndex = lastItemIndex,
        inProgressWorksheetId = inProgressWorksheetId,
        inProgressCorrectIndices = inProgressCorrectIndices,
        inProgressWrongIndices = inProgressWrongIndices,
        inProgressWrongAttemptCounts = inProgressWrongAttemptCounts,
        pendingSheetSync = pendingSheetSync,
        sheetRowNumber = profile.sheetRowNumber,
      )
    }
    return GradeProgressState()
  }

  fun recordsFor(grade: AralGrade = activeGrade): Map<Int, ActivityRecord> = progressFor(grade).records

  fun withGradeProgress(grade: AralGrade, state: GradeProgressState): LearnerProgressRecord {
    val updatedMap = gradeProgress.toMutableMap()
    if (grade != AralGrade.GRADE_4 && !updatedMap.containsKey(AralGrade.GRADE_4) && records.isNotEmpty()) {
      updatedMap[AralGrade.GRADE_4] = progressFor(AralGrade.GRADE_4)
    }
    updatedMap[grade] = state
    val activeState = updatedMap[grade] ?: state
    return copy(
      records = if (grade == activeGrade) activeState.records else recordsFor(activeGrade),
      lastWorksheetId = if (grade == activeGrade) activeState.lastWorksheetId else progressFor(activeGrade).lastWorksheetId,
      lastItemIndex = if (grade == activeGrade) activeState.lastItemIndex else progressFor(activeGrade).lastItemIndex,
      inProgressWorksheetId = if (grade == activeGrade) activeState.inProgressWorksheetId else progressFor(activeGrade).inProgressWorksheetId,
      inProgressCorrectIndices = if (grade == activeGrade) activeState.inProgressCorrectIndices else progressFor(activeGrade).inProgressCorrectIndices,
      inProgressWrongIndices = if (grade == activeGrade) activeState.inProgressWrongIndices else progressFor(activeGrade).inProgressWrongIndices,
      inProgressWrongAttemptCounts = if (grade == activeGrade) activeState.inProgressWrongAttemptCounts else progressFor(activeGrade).inProgressWrongAttemptCounts,
      pendingSheetSync = updatedMap.values.any { it.pendingSheetSync },
      gradeProgress = updatedMap,
    )
  }

  fun switchActiveGrade(grade: AralGrade): LearnerProgressRecord {
    val ensured = if (gradeProgress.isEmpty() && records.isNotEmpty()) {
      mapOf(AralGrade.GRADE_4 to progressFor(AralGrade.GRADE_4))
    } else {
      gradeProgress
    }
    val targetState = ensured[grade] ?: GradeProgressState()
    return copy(
      activeGrade = grade,
      gradeProgress = ensured,
      records = targetState.records,
      lastWorksheetId = targetState.lastWorksheetId,
      lastItemIndex = targetState.lastItemIndex,
      inProgressWorksheetId = targetState.inProgressWorksheetId,
      inProgressCorrectIndices = targetState.inProgressCorrectIndices,
      inProgressWrongIndices = targetState.inProgressWrongIndices,
      inProgressWrongAttemptCounts = targetState.inProgressWrongAttemptCounts,
      pendingSheetSync = ensured.values.any { it.pendingSheetSync },
    )
  }

  fun completedCountFor(grade: AralGrade = activeGrade): Int =
    progressFor(grade).records.values.count { it.isPassed90 }

  fun perfectCountFor(grade: AralGrade = activeGrade): Int =
    progressFor(grade).records.values.count { it.isPerfect100 }

  fun difficultyItemCountFor(grade: AralGrade = activeGrade): Int =
    progressFor(grade).records.values.sumOf { it.difficultyItemIndices.size }

  val completedCount: Int
    get() = completedCountFor(activeGrade)

  val perfectCount: Int
    get() = perfectCountFor(activeGrade)

  val overallProgressFraction: Float
    get() = if (activeGrade.totalWorksheets > 0) {
      (completedCount.toFloat() / activeGrade.totalWorksheets.toFloat()).coerceIn(0f, 1f)
    } else {
      0f
    }

  fun statusForWorksheet(worksheetId: Int, grade: AralGrade = activeGrade): ActivityMasteryStatus {
    return progressFor(grade).records[worksheetId]?.masteryStatus ?: ActivityMasteryStatus.NOT_STARTED
  }

  /**
   * All worksheets are open for learners to jump freely between activities.
   */
  @Suppress("UNUSED_PARAMETER")
  fun isWorksheetUnlocked(worksheetId: Int): Boolean = true

  fun recommendedStartIndexFor(worksheetId: Int, grade: AralGrade = activeGrade): Int {
    val gp = progressFor(grade)
    val rec = gp.records[worksheetId]
    if (rec != null) return rec.recommendedStartIndex()
    if (gp.inProgressWorksheetId == worksheetId) {
      val pendingWrong = gp.inProgressWrongIndices - gp.inProgressCorrectIndices
      if (pendingWrong.isNotEmpty()) return pendingWrong.min()
      return gp.lastItemIndex.coerceAtLeast(0)
    }
    return 0
  }

  /**
   * Determines the activity and item index where a returning learner left off or has wrong answers.
   */
  fun resumeTarget(grade: AralGrade = activeGrade): Pair<Int, Int> {
    val totalWs = grade.totalWorksheets
    if (totalWs <= 0) return 1 to 0
    val gp = progressFor(grade)
    val safeWs = gp.lastWorksheetId.coerceIn(1, totalWs)
    val lastRec = gp.records[safeWs]

    if (lastRec == null || !lastRec.isPassed90) {
      return safeWs to recommendedStartIndexFor(safeWs, grade)
    }
    if (lastRec.pendingWrongIndices.isNotEmpty() && !lastRec.completedRound) {
      return safeWs to lastRec.recommendedStartIndex()
    }

    val nextNeedAttention = (1..totalWs).firstOrNull { id ->
      val st = statusForWorksheet(id, grade)
      st == ActivityMasteryStatus.NOT_COMPLETED ||
        st == ActivityMasteryStatus.NOT_PASSED ||
        st == ActivityMasteryStatus.NOT_STARTED
    }
    if (nextNeedAttention != null) {
      return nextNeedAttention to recommendedStartIndexFor(nextNeedAttention, grade)
    }

    val nextImperfect = (1..totalWs).firstOrNull { id ->
      statusForWorksheet(id, grade) == ActivityMasteryStatus.PASSED
    }
    if (nextImperfect != null) {
      return nextImperfect to recommendedStartIndexFor(nextImperfect, grade)
    }

    return safeWs to 0
  }
}
