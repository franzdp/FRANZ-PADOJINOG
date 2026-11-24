package com.example

import com.example.data.SheetCoordinateHelper
import com.example.data.WorksheetCatalog
import com.example.data.WorksheetProblemsRepository
import com.example.model.ActivityMasteryStatus
import com.example.model.ActivityRecord
import com.example.model.AralGrade
import com.example.model.DivisibilityCheckProblem
import com.example.model.FractionOpProblem
import com.example.model.GcfLcmProblem
import com.example.model.GeometryMeasureProblem
import com.example.model.LearnerProfile
import com.example.model.LearnerProgressRecord
import com.example.model.MathProblem
import com.example.model.PolygonRegularProblem
import com.example.model.ThermometerProblem
import com.example.model.TimeConvertProblem
import com.example.data.GoogleSheetsRepository
import com.example.viewmodel.ActiveWorksheetUiState
import com.example.viewmodel.AnswerChecker
import org.json.JSONArray
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun all34Worksheets_haveProblems() {
    assertEquals(34, WorksheetCatalog.allWorksheets.size)
    for (ws in WorksheetCatalog.allWorksheets) {
      val problems = WorksheetProblemsRepository.getProblemsForWorksheet(ws.id)
      assertTrue("Worksheet ${ws.id} should not be empty", problems.isNotEmpty())
    }
  }

  @Test
  fun grade5_all32Worksheets_haveExactItemCounts() {
    val g5Worksheets = WorksheetCatalog.getWorksheets(AralGrade.GRADE_5)
    assertEquals(32, g5Worksheets.size)
    for (ws in g5Worksheets) {
      assertEquals(AralGrade.GRADE_5, ws.grade)
      val problems = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_5, ws.id)
      assertEquals(
        "Grade 5 Worksheet ${ws.id} (${ws.title}) itemCount mismatch",
        ws.itemCount,
        problems.size,
      )
      problems.forEachIndexed { idx, p ->
        assertEquals("Grade 5 WS ${ws.id} sequential itemNumber", idx + 1, p.itemNumber)
      }
    }
    // Spot check specific corrected Grade 5 items
    val ws1 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_5, 1)
    assertEquals(80112, (ws1[45] as MathProblem.NumberDiscsReadProblem).answer)

    val ws27 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_5, 27)
    val ws27Item3 = ws27[2] as MathProblem.WordProblemMultiFieldProblem
    assertEquals(858.0, ws27Item3.fields[0].expectedAnswer, 0.001)
    assertEquals(8.0, ws27Item3.fields[1].expectedAnswer, 0.001)

    val ws31 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_5, 31)
    assertEquals(3108, (ws31[7] as MathProblem.AngleKindProblem).diagramId)
    assertTrue("Right" in (ws31[7] as MathProblem.AngleKindProblem).validAnswers)
  }

  @Test
  fun answerChecker_normalizesWordsAndDigits() {
    assertEquals("one half", AnswerChecker.normalizeWords("One-Half"))
    assertEquals("7474", AnswerChecker.normalizeDigits("₱7,474.00"))
    assertTrue(AnswerChecker.checkFractionInput("3/4", 3, 4))
  }

  @Test
  fun sheetCoordinates_matchExactRow4Example_andGrade5Tabs() {
    assertEquals("B4", SheetCoordinateHelper.activityScoreCell(1, 4))
    assertEquals("C4", SheetCoordinateHelper.activityTimestampCell(1, 4))
    assertEquals("D4", SheetCoordinateHelper.activityScoreCell(2, 4))
    assertEquals("E4", SheetCoordinateHelper.activityTimestampCell(2, 4))
    assertEquals("BP4", SheetCoordinateHelper.activityScoreCell(34, 4))
    assertEquals("BQ4", SheetCoordinateHelper.activityTimestampCell(34, 4))

    assertEquals("B4", SheetCoordinateHelper.activityScoreCell(1, 4, AralGrade.GRADE_5.totalWorksheets))
    assertEquals("BL4", SheetCoordinateHelper.activityScoreCell(32, 4, AralGrade.GRADE_5.totalWorksheets))
    assertEquals("BM4", SheetCoordinateHelper.activityTimestampCell(32, 4, AralGrade.GRADE_5.totalWorksheets))
    assertEquals("BS", SheetCoordinateHelper.lastColumnLetters(AralGrade.GRADE_5))

    assertEquals("B4", SheetCoordinateHelper.activityScoreCell(1, 4, AralGrade.GRADE_6.totalWorksheets))
    assertEquals("BR4", SheetCoordinateHelper.activityScoreCell(35, 4, AralGrade.GRADE_6.totalWorksheets))
    assertEquals("BS4", SheetCoordinateHelper.activityTimestampCell(35, 4, AralGrade.GRADE_6.totalWorksheets))
    assertEquals("BY", SheetCoordinateHelper.lastColumnLetters(AralGrade.GRADE_6))
  }

  @Test
  fun grade6_all35Worksheets_haveExactItemCounts_andSampleAnswers() {
    val g6Worksheets = WorksheetCatalog.getWorksheets(AralGrade.GRADE_6)
    assertEquals(35, g6Worksheets.size)
    for (ws in g6Worksheets) {
      assertEquals(AralGrade.GRADE_6, ws.grade)
      val problems = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_6, ws.id)
      assertEquals(
        "Grade 6 Worksheet ${ws.id} (${ws.title}) itemCount mismatch",
        ws.itemCount,
        problems.size,
      )
      problems.forEachIndexed { idx, p ->
        assertEquals("Grade 6 WS ${ws.id} sequential itemNumber", idx + 1, p.itemNumber)
      }
    }

    // WS 1 item 1: 245 divisible by 5
    val ws1 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_6, 1)
    assertEquals(listOf(5), (ws1[0] as DivisibilityCheckProblem).validDivisors)

    // WS 5 item 1: 3 x (13 x 10) = 390
    val ws5 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_6, 5)
    assertEquals(390.0, (ws5[0] as MathProblem.OrderOfOpsProblem).expectedValue, 0.001)

    // WS 9 item 1: GCF(10, 50) = 10
    val ws9 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_6, 9)
    assertEquals(10, (ws9[0] as GcfLcmProblem).expectedGcf)

    // WS 17 item 27: 1 3/5 x 5 1/2 = 44/5 (8 4/5)
    val ws17 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_6, 17)
    val item27 = ws17[26] as FractionOpProblem
    assertEquals(44, item27.expectedNum)
    assertEquals(5, item27.expectedDen)

    // WS 23 item 25: 45/8 min (5.625)
    val ws23 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_6, 23)
    val item25 = ws23[24] as FractionOpProblem
    assertEquals(45, item25.expectedNum)
    assertEquals(8, item25.expectedDen)

    // WS 26 item 1 regular, item 2 irregular
    val ws26 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_6, 26)
    assertTrue((ws26[0] as PolygonRegularProblem).isRegular)
    assertFalse((ws26[1] as PolygonRegularProblem).isRegular)

    // WS 28 item 4: 5:45 PM -> 17:45
    val ws28 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_6, 28)
    assertEquals("17:45", (ws28[3] as TimeConvertProblem).displayAnswer)

    // WS 32 item 4: 20*5*15 = 1500
    val ws32 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_6, 32)
    assertEquals(1500.0, (ws32[3] as GeometryMeasureProblem).fields[0].expectedAnswer, 0.001)

    // WS 35 item 4: -7.0 C
    val ws35 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_6, 35)
    assertEquals(-7.0, (ws35[3] as ThermometerProblem).targetCelsius, 0.001)
  }

  @Test
  fun masteryStatusesAndFreeJumping_workAcrossAll5States() {
    assertFalse(SheetCoordinateHelper.hasPassedMastery(17, 19))
    assertTrue(SheetCoordinateHelper.hasPassedMastery(18, 20))

    val notStarted = ActivityRecord(1, 0, 20, 0, "")
    assertEquals(ActivityMasteryStatus.NOT_STARTED, notStarted.masteryStatus)

    val notCompleted = ActivityRecord(
      worksheetId = 2,
      correctCount = 4,
      totalItems = 20,
      stars = 1,
      timestamp = "2026-11-23 10:00:00 AM",
      correctIndices = setOf(0, 1, 3, 4),
      wrongIndices = setOf(2),
      lastItemIndex = 5,
      completedRound = false,
    )
    assertEquals(ActivityMasteryStatus.NOT_COMPLETED, notCompleted.masteryStatus)
    assertEquals(5, notCompleted.recommendedStartIndex())

    val notPassed = ActivityRecord(
      worksheetId = 3,
      correctCount = 14,
      totalItems = 20,
      stars = 2,
      timestamp = "2026-11-23 10:05:00 AM",
      correctIndices = (0..13).toSet(),
      wrongIndices = (14..19).toSet(),
      lastItemIndex = 19,
      completedRound = true,
    )
    assertEquals(ActivityMasteryStatus.NOT_PASSED, notPassed.masteryStatus)
    assertEquals(14, notPassed.recommendedStartIndex())

    val passed = ActivityRecord(
      worksheetId = 4,
      correctCount = 18,
      totalItems = 20,
      stars = 3,
      timestamp = "2026-11-23 10:10:00 AM",
      correctIndices = (0..17).toSet(),
      wrongIndices = setOf(18, 19),
      lastItemIndex = 19,
      completedRound = true,
    )
    assertEquals(ActivityMasteryStatus.PASSED, passed.masteryStatus)
    assertEquals(18, passed.recommendedStartIndex())

    val perfect = ActivityRecord(
      worksheetId = 5,
      correctCount = 20,
      totalItems = 20,
      stars = 3,
      timestamp = "2026-11-23 10:15:00 AM",
      correctIndices = (0..19).toSet(),
      wrongIndices = emptySet(),
      lastItemIndex = 19,
      completedRound = true,
    )
    assertEquals(ActivityMasteryStatus.PERFECT, perfect.masteryStatus)

    val g5Perfect = ActivityRecord(
      worksheetId = 1,
      correctCount = 48,
      totalItems = 48,
      stars = 3,
      timestamp = "2026-11-23 10:20:00 AM",
      correctIndices = (0..47).toSet(),
      wrongIndices = emptySet(),
      lastItemIndex = 47,
      completedRound = true,
    )

    val learner = LearnerProgressRecord(
      profile = LearnerProfile("Juan Dela Cruz", "Grade 4", "Bagacay ES", "AA:BB:CC:DD:EE:FF", 4),
      records = mapOf(2 to notCompleted, 3 to notPassed, 4 to passed, 5 to perfect),
      lastWorksheetId = 3,
      lastItemIndex = 19,
      gradeProgress = mapOf(
        AralGrade.GRADE_5 to com.example.model.GradeProgressState(
          records = mapOf(1 to g5Perfect),
          lastWorksheetId = 1,
          lastItemIndex = 47,
          sheetRowNumber = 5,
        ),
      ),
    )
    assertTrue(learner.isWorksheetUnlocked(1))
    assertTrue(learner.isWorksheetUnlocked(34))
    assertEquals(2, learner.completedCount)
    assertEquals(1, learner.perfectCount)
    assertEquals(3 to 14, learner.resumeTarget())
    assertEquals(1, learner.completedCountFor(AralGrade.GRADE_5))
    assertEquals(1, learner.perfectCountFor(AralGrade.GRADE_5))
    assertEquals(2, learner.completedCountFor(AralGrade.GRADE_4))
  }

  @Test
  fun scoreParsing_roundTripsCorrectly() {
    val formatted = SheetCoordinateHelper.formatScoreForSheet(18, 20)
    assertEquals("90% (18/20)", formatted)
    assertEquals(18 to 20, SheetCoordinateHelper.parseScoreFromSheet(formatted, 20))
    assertEquals("juan dela cruz", SheetCoordinateHelper.normalizeLearnerName("  Juan   Dela Cruz "))
  }

  @Test
  fun twoTryFlow_blocksAdvanceAfterFirstWrong_allowsAfterSecondWrong_andTracksPendingWrong() {
    val ws = WorksheetCatalog.getWorksheets(AralGrade.GRADE_4).first()
    val problems = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_4, ws.id)
    val initial = ActiveWorksheetUiState(worksheet = ws, allProblems = problems, currentIndex = 0)

    assertEquals(0, initial.currentAttempts)
    assertFalse(initial.awaitingSecondTry)
    assertTrue(initial.canAdvanceFromCurrent)

    // 1st wrong try: blocked from advancing, awaiting 2nd try
    val afterFirstWrong = initial.copy(
      wrongAttemptIndices = setOf(0),
      attemptCounts = mapOf(0 to 1),
      showHint = true,
    )
    assertEquals(1, afterFirstWrong.currentAttempts)
    assertTrue(afterFirstWrong.awaitingSecondTry)
    assertFalse(afterFirstWrong.canAdvanceFromCurrent)
    assertEquals(listOf(0), afterFirstWrong.pendingWrongIndices)

    // 2nd wrong try: allowed to advance and return later
    val afterSecondWrong = afterFirstWrong.copy(
      attemptCounts = mapOf(0 to 2),
    )
    assertEquals(2, afterSecondWrong.currentAttempts)
    assertFalse(afterSecondWrong.awaitingSecondTry)
    assertTrue(afterSecondWrong.canAdvanceFromCurrent)
    assertEquals(listOf(0), afterSecondWrong.pendingWrongIndices)

    // Solved on 2nd try: clears wrong & attempt state
    val afterSecondCorrect = afterFirstWrong.copy(
      solvedCorrectIndices = setOf(0),
      wrongAttemptIndices = emptySet(),
      attemptCounts = emptyMap(),
    )
    assertTrue(afterSecondCorrect.isCurrentSolved)
    assertFalse(afterSecondCorrect.awaitingSecondTry)
    assertTrue(afterSecondCorrect.canAdvanceFromCurrent)
    assertTrue(afterSecondCorrect.pendingWrongIndices.isEmpty())
  }

  @Test
  fun allGrades_everyProblemHasNonBlankGuidingHint() {
    for (grade in AralGrade.entries) {
      for (ws in WorksheetCatalog.getWorksheets(grade)) {
        val problems = WorksheetProblemsRepository.getProblemsForWorksheet(grade, ws.id)
        for (p in problems) {
          assertTrue(
            "${grade.shortLabel} WS ${ws.id} item ${p.itemNumber} should have a non-blank hint",
            p.hint.isNotBlank(),
          )
        }
      }
    }
  }

  @Test
  fun centralWebAppSync_validatesUrl_andFormatsGradeTabsAndScores() {
    assertFalse(GoogleSheetsRepository.isConfiguredWebAppUrl(null))
    assertFalse(GoogleSheetsRepository.isConfiguredWebAppUrl(""))
    assertFalse(GoogleSheetsRepository.isConfiguredWebAppUrl("YOUR_APPS_SCRIPT_WEB_APP_URL"))
    assertFalse(GoogleSheetsRepository.isConfiguredWebAppUrl("https://example.com/script"))
    assertTrue(
      GoogleSheetsRepository.isConfiguredWebAppUrl(
        "https://script.google.com/macros/s/AKfycbx1234567890abcdefg/exec",
      ),
    )

    assertEquals(
      listOf("Grade 2", "Grade 3", "Grade 4", "Grade 5", "Grade 6"),
      AralGrade.entries.map { it.sheetTabName },
    )
    assertEquals(36, AralGrade.GRADE_2.totalWorksheets)
    assertEquals(24, AralGrade.GRADE_3.totalWorksheets)
    assertEquals(36, AralGrade.GRADE_2.sheetColumnSlots)
    assertEquals(24, AralGrade.GRADE_3.sheetColumnSlots)
    assertEquals(34, AralGrade.GRADE_4.sheetColumnSlots)
    assertEquals(32, AralGrade.GRADE_5.sheetColumnSlots)
    assertEquals(35, AralGrade.GRADE_6.sheetColumnSlots)

    val act1 = ActivityRecord(
      worksheetId = 1,
      correctCount = 18,
      totalItems = 20,
      stars = 3,
      timestamp = "2026-11-23 02:50:00 PM",
    )
    val gp5 = com.example.model.GradeProgressState(
      records = mapOf(1 to act1),
      lastWorksheetId = 2,
      lastItemIndex = 4,
      sheetRowNumber = 4,
    )
    val learner = LearnerProgressRecord(
      profile = LearnerProfile(
        name = "Maria Santos",
        gradeLevel = "Grade 5",
        school = "Bagacay Elementary School",
        deviceAddress = "AA:BB:CC:DD:EE:FF",
      ),
      activeGrade = AralGrade.GRADE_5,
      gradeProgress = mapOf(AralGrade.GRADE_5 to gp5),
    )

    assertEquals(18, learner.recordsFor(AralGrade.GRADE_5)[1]?.correctCount)
    assertEquals(4, learner.progressFor(AralGrade.GRADE_5).sheetRowNumber)
    assertEquals("90% (18/20)", SheetCoordinateHelper.formatScoreForSheet(18, 20))
    assertEquals(2 to 0, learner.resumeTarget(AralGrade.GRADE_5).first to 0)
    assertEquals(1 to 0, learner.resumeTarget(AralGrade.GRADE_2))
  }

  @Test
  fun grade2_all36Worksheets_haveExactItemCounts_andSampleChecks() {
    val g2Worksheets = WorksheetCatalog.getWorksheets(AralGrade.GRADE_2)
    assertEquals(36, g2Worksheets.size)
    for (ws in g2Worksheets) {
      assertEquals(AralGrade.GRADE_2, ws.grade)
      val problems = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_2, ws.id)
      assertEquals(
        "Grade 2 Worksheet ${ws.id} (${ws.title}) itemCount mismatch",
        ws.itemCount,
        problems.size,
      )
      problems.forEachIndexed { idx, p ->
        assertEquals("Grade 2 WS ${ws.id} sequential itemNumber", idx + 1, p.itemNumber)
      }
    }

    val ws1 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_2, 1)
    assertEquals(5, (ws1[0] as com.example.model.CountObjectsProblem).count)

    val ws36 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_2, 36)
    assertEquals("30", (ws36[0] as com.example.model.CalendarProblem).displayAnswer)
  }

  @Test
  fun grade3_all24Worksheets_haveExactItemCounts_andSampleChecks() {
    val g3Worksheets = WorksheetCatalog.getWorksheets(AralGrade.GRADE_3)
    assertEquals(24, g3Worksheets.size)
    for (ws in g3Worksheets) {
      assertEquals(AralGrade.GRADE_3, ws.grade)
      val problems = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_3, ws.id)
      assertEquals(
        "Grade 3 Worksheet ${ws.id} (${ws.title}) itemCount mismatch",
        ws.itemCount,
        problems.size,
      )
      problems.forEachIndexed { idx, p ->
        assertEquals("Grade 3 WS ${ws.id} sequential itemNumber", idx + 1, p.itemNumber)
      }
    }

    val ws1 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_3, 1)
    val ws1Item1 = ws1[0] as MathProblem.PlaceValueProblem
    assertEquals("Ones", ws1Item1.expectedPlaceValue)
    assertTrue(ws1Item1.askPlaceValue)
    assertFalse(ws1Item1.askValue)

    val ws7 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_3, 7)
    val ws7Item36 = ws7[35] as com.example.model.SkipCountGridProblem
    assertEquals(listOf(90), ws7Item36.expectedBlanks)

    val ws18 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_3, 18)
    val ws18Item20 = ws18[19] as com.example.model.MultDivSentenceProblem
    assertEquals(10, ws18Item20.expectedFirst)
    assertEquals(8, ws18Item20.expectedResult)

    val ws24 = WorksheetProblemsRepository.getProblemsForWorksheet(AralGrade.GRADE_3, 24)
    assertEquals("FLAT", (ws24[27] as com.example.model.LineSurfaceProblem).correctOption)
  }

  @Test
  fun progressReportData_flagsDifficultyAndRepeatedWrongItems() {
    val act1 = ActivityRecord(
      worksheetId = 1,
      correctCount = 17,
      totalItems = 20,
      stars = 2,
      timestamp = "2026-11-23 03:20:00 PM",
      correctIndices = (0..19).toSet() - setOf(2, 5, 9),
      wrongIndices = setOf(2, 5, 9),
      wrongAttemptCounts = mapOf(
        0 to 2, // solved after 2 wrong tries
        2 to 3, // tried 3x and still wrong
        5 to 2, // tried 2x and still wrong
        9 to 1, // tried 1x and still wrong
      ),
      lastItemIndex = 19,
      completedRound = true,
    )
    assertEquals(listOf(0, 2, 5, 9), act1.difficultyItemIndices)
    assertEquals(listOf(2, 5), act1.repeatedStillWrongIndices)

    val learner = LearnerProgressRecord(
      profile = LearnerProfile(
        name = "Pedro Reyes",
        gradeLevel = "Grade 4",
        school = "Bagacay Elementary School",
        deviceAddress = "AA:BB:CC:DD:EE:FF",
      ),
      activeGrade = AralGrade.GRADE_4,
      gradeProgress = mapOf(
        AralGrade.GRADE_4 to com.example.model.GradeProgressState(
          records = mapOf(1 to act1),
          lastWorksheetId = 1,
          lastItemIndex = 19,
        ),
      ),
    )

    val report = com.example.data.ProgressReportPdfGenerator.buildReportData(
      learner = learner,
      grade = AralGrade.GRADE_4,
      generatedAt = "2026-11-23 03:25 PM",
    )

    assertEquals("Pedro Reyes", report.learnerName)
    assertEquals(34, report.totalWorksheets)
    assertEquals(1, report.notPassedCount)
    assertEquals(33, report.notStartedCount)
    assertEquals(1, report.difficultyActivities.size)
    assertEquals(4, report.totalDifficultyItems)
    assertEquals(2, report.totalRepeatedStillWrongItems)

    val row1 = report.activityRows.first()
    assertEquals("17/20 (85%)", row1.scoreText)
    assertEquals(4, row1.difficultyItems.size)
    assertEquals(2, row1.repeatedStillWrongItems.size)
    assertTrue(row1.difficultyItems.any { it.itemNumber == 3 && it.wrongTries == 3 && it.stillWrong })
    assertTrue(row1.difficultyItems.any { it.itemNumber == 1 && it.wrongTries == 2 && !it.stillWrong })
  }
}
