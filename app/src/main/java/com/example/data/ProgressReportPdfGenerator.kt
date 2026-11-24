package com.example.data

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.example.R
import com.example.model.ActivityMasteryStatus
import com.example.model.AralGrade
import com.example.model.CalendarProblem
import com.example.model.CompareWordProblem
import com.example.model.CountObjectsProblem
import com.example.model.DivisibilityCheckProblem
import com.example.model.DivisibilityRuleJudgeProblem
import com.example.model.EqualPartsPartitionProblem
import com.example.model.EquationPartProblem
import com.example.model.ExpandedFormProblem
import com.example.model.FractionOpProblem
import com.example.model.GcfLcmProblem
import com.example.model.GeometryMeasureProblem
import com.example.model.InteractiveFractionProblem
import com.example.model.LearnerProgressRecord
import com.example.model.LineSurfaceProblem
import com.example.model.MathProblem
import com.example.model.MultDivSentenceProblem
import com.example.model.OrdinalShapeProblem
import com.example.model.PolygonRegularProblem
import com.example.model.PolygonTableProblem
import com.example.model.ShapeClassificationProblem
import com.example.model.SkipCountGridProblem
import com.example.model.SmallestDigitDivisibilityProblem
import com.example.model.StampObjectsProblem
import com.example.model.ThermometerProblem
import com.example.model.TimeConvertProblem
import com.example.model.VisualAddSubSentenceProblem
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DifficultyItemDetail(
  val itemNumber: Int,
  val wrongTries: Int,
  val stillWrong: Boolean,
  val promptPreview: String,
) {
  val statusTag: String
    get() = when {
      stillWrong && wrongTries >= 2 -> "Tried ${wrongTries}x — Still Wrong"
      stillWrong -> "Still Wrong (${wrongTries}x)"
      else -> "Tried ${wrongTries}x — Solved Later"
    }
}

data class ActivityReportRow(
  val worksheetId: Int,
  val title: String,
  val itemCount: Int,
  val scoreText: String,
  val status: ActivityMasteryStatus,
  val timestamp: String,
  val difficultyItems: List<DifficultyItemDetail>,
) {
  val statusLabel: String
    get() = when (status) {
      ActivityMasteryStatus.PERFECT -> "Perfect ★"
      ActivityMasteryStatus.PASSED -> "Passed"
      ActivityMasteryStatus.NOT_PASSED -> "Not Passed"
      ActivityMasteryStatus.NOT_COMPLETED -> "In Progress"
      ActivityMasteryStatus.NOT_STARTED -> "Not Started"
    }

  val repeatedStillWrongItems: List<DifficultyItemDetail>
    get() = difficultyItems.filter { it.stillWrong && it.wrongTries >= 2 }
}

data class ProgressReportData(
  val learnerName: String,
  val gradeLevel: String,
  val school: String,
  val deviceAddress: String,
  val aralGrade: AralGrade,
  val generatedAt: String,
  val passedCount: Int,
  val perfectCount: Int,
  val notPassedCount: Int,
  val notCompletedCount: Int,
  val notStartedCount: Int,
  val totalWorksheets: Int,
  val activityRows: List<ActivityReportRow>,
) {
  val difficultyActivities: List<ActivityReportRow>
    get() = activityRows.filter { it.difficultyItems.isNotEmpty() }

  val totalDifficultyItems: Int
    get() = activityRows.sumOf { it.difficultyItems.size }

  val totalRepeatedStillWrongItems: Int
    get() = activityRows.sumOf { it.repeatedStillWrongItems.size }
}

data class MessengerShareOutcome(
  val launchedDirectlyToMessenger: Boolean,
  val chooserOpened: Boolean,
  val message: String,
)

object ProgressReportPdfGenerator {
  private const val PAGE_WIDTH = 595
  private const val PAGE_HEIGHT = 842
  private const val MARGIN_X = 34f
  private const val CONTENT_WIDTH = PAGE_WIDTH - MARGIN_X * 2f

  private val MESSENGER_PACKAGES = listOf(
    "com.facebook.orca",
    "com.facebook.mlite",
  )

  fun buildReportData(
    learner: LearnerProgressRecord,
    grade: AralGrade,
    generatedAt: String = currentReportTimestamp(),
  ): ProgressReportData {
    val worksheets = WorksheetCatalog.getWorksheets(grade)
    val records = learner.recordsFor(grade)

    val rows = worksheets.map { ws ->
      val rec = records[ws.id]
      val status = rec?.masteryStatus ?: ActivityMasteryStatus.NOT_STARTED
      val scoreText = if (rec == null || status == ActivityMasteryStatus.NOT_STARTED) {
        "-- / ${ws.itemCount}"
      } else {
        "${rec.correctCount}/${rec.totalItems} (${rec.percent}%)"
      }

      val difficultyList = if (rec != null && rec.difficultyItemIndices.isNotEmpty()) {
        val problems = WorksheetProblemsRepository.getProblemsForWorksheet(grade, ws.id)
        rec.difficultyItemIndices.map { idx ->
          val problem = problems.getOrNull(idx)
          val tries = rec.wrongAttemptsFor(idx).coerceAtLeast(1)
          val stillWrong = idx !in rec.correctIndices
          DifficultyItemDetail(
            itemNumber = idx + 1,
            wrongTries = tries,
            stillWrong = stillWrong,
            promptPreview = problem?.let { summarizeProblem(it) } ?: "Item #${idx + 1}",
          )
        }
      } else {
        emptyList()
      }

      ActivityReportRow(
        worksheetId = ws.id,
        title = ws.title,
        itemCount = ws.itemCount,
        scoreText = scoreText,
        status = status,
        timestamp = rec?.timestamp?.takeIf { it.isNotBlank() } ?: "--",
        difficultyItems = difficultyList,
      )
    }

    return ProgressReportData(
      learnerName = learner.profile.name,
      gradeLevel = learner.profile.gradeLevel,
      school = learner.profile.school,
      deviceAddress = learner.profile.deviceAddress,
      aralGrade = grade,
      generatedAt = generatedAt,
      passedCount = rows.count { it.status == ActivityMasteryStatus.PASSED || it.status == ActivityMasteryStatus.PERFECT },
      perfectCount = rows.count { it.status == ActivityMasteryStatus.PERFECT },
      notPassedCount = rows.count { it.status == ActivityMasteryStatus.NOT_PASSED },
      notCompletedCount = rows.count { it.status == ActivityMasteryStatus.NOT_COMPLETED },
      notStartedCount = rows.count { it.status == ActivityMasteryStatus.NOT_STARTED },
      totalWorksheets = worksheets.size,
      activityRows = rows,
    )
  }

  fun generatePdf(
    context: Context,
    learner: LearnerProgressRecord,
    grade: AralGrade,
  ): File {
    val report = buildReportData(learner, grade)
    val dir = File(context.cacheDir, "progress_reports").apply { mkdirs() }
    val safeName = report.learnerName
      .trim()
      .replace(Regex("[^A-Za-z0-9_-]+"), "_")
      .trim('_')
      .ifBlank { "Learner" }
    val fileName = "ARAL_Math_${safeName}_Grade${grade.gradeNumber}_Progress_Report.pdf"
    val outFile = File(dir, fileName)

    val logoBitmap = runCatching {
      BitmapFactory.decodeResource(context.resources, R.drawable.bagacay_school_logo)
    }.getOrNull()

    val pdf = PdfDocument()
    try {
      renderPdfPages(pdf, report, logoBitmap)
      FileOutputStream(outFile).use { fos ->
        pdf.writeTo(fos)
      }
    } finally {
      pdf.close()
      logoBitmap?.recycle()
    }
    return outFile
  }

  fun sharePdfViaMessenger(
    context: Context,
    pdfFile: File,
    learnerName: String,
    grade: AralGrade,
  ): MessengerShareOutcome {
    val uri = FileProvider.getUriForFile(
      context,
      "${context.packageName}.fileprovider",
      pdfFile,
    )
    val summarySubject = "ARAL Math Progress Report - $learnerName (${grade.shortLabel})"
    val summaryText = "ARAL Math Progress Report for $learnerName (${grade.displayTitle}). Attached PDF includes per-activity scores and difficulty items."

    fun buildBaseSendIntent(): Intent = Intent(Intent.ACTION_SEND).apply {
      type = "application/pdf"
      putExtra(Intent.EXTRA_STREAM, uri)
      putExtra(Intent.EXTRA_SUBJECT, summarySubject)
      putExtra(Intent.EXTRA_TEXT, summaryText)
      clipData = ClipData.newRawUri("ARAL Math Progress Report PDF", uri)
      addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    val pm = context.packageManager
    for (pkg in MESSENGER_PACKAGES) {
      val directIntent = buildBaseSendIntent().apply { setPackage(pkg) }
      if (directIntent.resolveActivity(pm) != null) {
        val launched = runCatching { context.startActivity(directIntent) }.isSuccess
        if (launched) {
          return MessengerShareOutcome(
            launchedDirectlyToMessenger = true,
            chooserOpened = false,
            message = "PDF Progress Report ready! Opening Messenger — choose who to send it to.",
          )
        }
      }
    }

    val chooser = Intent.createChooser(buildBaseSendIntent(), "Send Progress Report PDF via Messenger").apply {
      addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    val chooserLaunched = runCatching { context.startActivity(chooser) }.isSuccess
    return if (chooserLaunched) {
      MessengerShareOutcome(
        launchedDirectlyToMessenger = false,
        chooserOpened = true,
        message = "PDF Progress Report created! Select Messenger (or an app) to send your attached PDF.",
      )
    } else {
      MessengerShareOutcome(
        launchedDirectlyToMessenger = false,
        chooserOpened = false,
        message = "PDF Progress Report saved (${pdfFile.name}), but no sharing app was found on this device.",
      )
    }
  }

  fun openPdfFile(context: Context, pdfFile: File): Boolean {
    val uri = FileProvider.getUriForFile(
      context,
      "${context.packageName}.fileprovider",
      pdfFile,
    )
    val viewIntent = Intent(Intent.ACTION_VIEW).apply {
      setDataAndType(uri, "application/pdf")
      addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
      addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    return runCatching { context.startActivity(viewIntent) }.isSuccess
  }

  private fun renderPdfPages(pdf: PdfDocument, report: ProgressReportData, logoBitmap: Bitmap?) {
    val headerBgPaint = Paint().apply { color = Color.rgb(21, 101, 192) }
    val softBluePaint = Paint().apply { color = Color.rgb(227, 242, 253) }
    val softRedPaint = Paint().apply { color = Color.rgb(255, 235, 238) }
    val softAmberPaint = Paint().apply { color = Color.rgb(255, 248, 225) }
    val tableHeaderPaint = Paint().apply { color = Color.rgb(187, 222, 251) }
    val rowAltPaint = Paint().apply { color = Color.rgb(248, 250, 252) }
    val borderPaint = Paint().apply {
      color = Color.rgb(197, 210, 224)
      style = Paint.Style.STROKE
      strokeWidth = 1f
    }

    val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.WHITE
      textSize = 15f
      typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    val subtitlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.rgb(255, 236, 179)
      textSize = 10f
      typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    val sectionPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.rgb(13, 71, 161)
      textSize = 11.5f
      typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    val boldPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.rgb(24, 32, 48)
      textSize = 9.2f
      typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    val bodyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.rgb(38, 48, 64)
      textSize = 9f
      typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    }
    val mutedPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.rgb(92, 104, 122)
      textSize = 8.2f
    }
    val alertPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.rgb(183, 28, 28)
      textSize = 8.8f
      typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    val amberPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.rgb(198, 92, 0)
      textSize = 8.8f
      typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.rgb(46, 125, 50)
      textSize = 8.8f
      typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }
    val bluePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.rgb(21, 101, 192)
      textSize = 8.8f
      typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    var pageNumber = 1
    var page = pdf.startPage(PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNumber).create())
    var canvas: Canvas = page.canvas
    var y = 26f

    fun finishCurrentAndStartNewPage(continuationTitle: String) {
      canvas.drawText(
        "ARAL MATH for BAGACAY ES • ${report.learnerName} • Page $pageNumber",
        MARGIN_X,
        PAGE_HEIGHT - 18f,
        mutedPaint,
      )
      pdf.finishPage(page)
      pageNumber += 1
      page = pdf.startPage(PdfDocument.PageInfo.Builder(PAGE_WIDTH, PAGE_HEIGHT, pageNumber).create())
      canvas = page.canvas
      y = 32f
      canvas.drawText(
        "ARAL MATH for BAGACAY ES — ${report.aralGrade.displayTitle} ($continuationTitle)",
        MARGIN_X,
        y,
        sectionPaint,
      )
      y += 16f
    }

    fun ensureSpace(neededHeight: Float, continuationTitle: String) {
      if (y + neededHeight > PAGE_HEIGHT - 38f) {
        finishCurrentAndStartNewPage(continuationTitle)
      }
    }

    // Header Banner
    canvas.drawRoundRect(RectF(MARGIN_X, y, PAGE_WIDTH - MARGIN_X, y + 60f), 8f, 8f, headerBgPaint)
    val textStartX = if (logoBitmap != null) {
      val logoSize = 44f
      val logoLeft = MARGIN_X + 10f
      val logoTop = y + 8f
      val whiteCirclePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }
      canvas.drawCircle(logoLeft + logoSize / 2f, logoTop + logoSize / 2f, logoSize / 2f + 1.5f, whiteCirclePaint)
      canvas.drawBitmap(
        logoBitmap,
        null,
        Rect(logoLeft.toInt(), logoTop.toInt(), (logoLeft + logoSize).toInt(), (logoTop + logoSize).toInt()),
        null,
      )
      logoLeft + logoSize + 12f
    } else {
      MARGIN_X + 12f
    }
    canvas.drawText("ARAL MATH for BAGACAY ES — PROGRESS REPORT", textStartX, y + 22f, titlePaint)
    canvas.drawText(
      "${report.aralGrade.displayTitle}  |  Generated: ${report.generatedAt}",
      textStartX,
      y + 38f,
      subtitlePaint,
    )
    canvas.drawText(
      "This is an Innovation of FRANZ D. PADOJINOG",
      textStartX,
      y + 52f,
      subtitlePaint,
    )
    y += 70f

    // Learner Info + Mastery Summary Box
    canvas.drawRoundRect(RectF(MARGIN_X, y, PAGE_WIDTH - MARGIN_X, y + 66f), 6f, 6f, softBluePaint)
    canvas.drawRoundRect(RectF(MARGIN_X, y, PAGE_WIDTH - MARGIN_X, y + 66f), 6f, 6f, borderPaint)
    canvas.drawText("Learner: ${report.learnerName}", MARGIN_X + 10f, y + 16f, boldPaint)
    canvas.drawText("Grade & School: ${report.gradeLevel} — ${report.school}", MARGIN_X + 10f, y + 31f, bodyPaint)
    canvas.drawText("Device ID: ${report.deviceAddress}", MARGIN_X + 10f, y + 45f, mutedPaint)
    canvas.drawText(
      "Summary: Passed (>=90%): ${report.passedCount}/${report.totalWorksheets}   |   Perfect (100% ★): ${report.perfectCount}   |   Not Passed: ${report.notPassedCount}   |   In Progress: ${report.notCompletedCount}   |   Not Started: ${report.notStartedCount}",
      MARGIN_X + 10f,
      y + 59f,
      boldPaint,
    )
    y += 78f

    // Section 1: Difficulty & Repeated-Wrong Items
    canvas.drawText("1. DIFFICULTY ITEMS (Tried >1x or Still Wrong — Needs Review)", MARGIN_X, y, sectionPaint)
    y += 12f

    if (report.difficultyActivities.isEmpty()) {
      canvas.drawRoundRect(RectF(MARGIN_X, y, PAGE_WIDTH - MARGIN_X, y + 26f), 6f, 6f, softAmberPaint)
      canvas.drawText(
        "No repeated-wrong or unresolved difficulty items recorded in ${report.aralGrade.shortLabel} yet.",
        MARGIN_X + 10f,
        y + 17f,
        bodyPaint,
      )
      y += 38f
    } else {
      val summaryBoxHeight = 24f
      canvas.drawRoundRect(RectF(MARGIN_X, y, PAGE_WIDTH - MARGIN_X, y + summaryBoxHeight), 6f, 6f, softRedPaint)
      canvas.drawText(
        "Flagged Difficulty Items: ${report.totalDifficultyItems} item(s) across ${report.difficultyActivities.size} activity(ies)  •  Tried >1x & Still Wrong: ${report.totalRepeatedStillWrongItems}",
        MARGIN_X + 10f,
        y + 16f,
        alertPaint,
      )
      y += summaryBoxHeight + 8f

      for (act in report.difficultyActivities) {
        ensureSpace(24f, "Difficulty Items Continued")
        canvas.drawText(
          "Activity ${act.worksheetId}: ${ellipsize(act.title, 48)}  (${act.scoreText} — ${act.statusLabel})",
          MARGIN_X + 4f,
          y + 11f,
          boldPaint,
        )
        y += 16f

        for (item in act.difficultyItems) {
          ensureSpace(22f, "Difficulty Items Continued")
          val badgePaint = when {
            item.stillWrong && item.wrongTries >= 2 -> alertPaint
            item.stillWrong -> amberPaint
            else -> bluePaint
          }
          canvas.drawText(
            "• Item #${item.itemNumber} [${item.statusTag}]",
            MARGIN_X + 14f,
            y + 9f,
            badgePaint,
          )
          val snippet = ellipsize(item.promptPreview, 62)
          canvas.drawText(snippet, MARGIN_X + 215f, y + 9f, bodyPaint)
          y += 14f
        }
        y += 6f
      }
      y += 6f
    }

    // Section 2: Activity-by-Activity Results Table
    ensureSpace(46f, "Activity Results")
    canvas.drawText("2. ACTIVITY-BY-ACTIVITY PROGRESS (${report.aralGrade.shortLabel})", MARGIN_X, y, sectionPaint)
    y += 10f

    fun drawTableHeader() {
      canvas.drawRect(MARGIN_X, y, PAGE_WIDTH - MARGIN_X, y + 20f, tableHeaderPaint)
      canvas.drawRect(MARGIN_X, y, PAGE_WIDTH - MARGIN_X, y + 20f, borderPaint)
      canvas.drawText("Act", MARGIN_X + 6f, y + 13f, boldPaint)
      canvas.drawText("Activity Title", MARGIN_X + 34f, y + 13f, boldPaint)
      canvas.drawText("Score", MARGIN_X + 240f, y + 13f, boldPaint)
      canvas.drawText("Status", MARGIN_X + 318f, y + 13f, boldPaint)
      canvas.drawText("Difficulty Items", MARGIN_X + 392f, y + 13f, boldPaint)
      y += 20f
    }

    if (report.activityRows.isEmpty()) {
      canvas.drawText("No activities available yet for ${report.aralGrade.shortLabel}.", MARGIN_X + 6f, y + 14f, bodyPaint)
      y += 24f
    } else {
      drawTableHeader()
      report.activityRows.forEachIndexed { idx, row ->
        val diffText = if (row.difficultyItems.isEmpty()) {
          "None"
        } else {
          row.difficultyItems.joinToString(", ") {
            if (it.stillWrong) "#${it.itemNumber}(${it.wrongTries}x!)" else "#${it.itemNumber}(${it.wrongTries}x)"
          }
        }
        val rowHeight = if (diffText.length > 26) 26f else 19f
        if (y + rowHeight > PAGE_HEIGHT - 38f) {
          finishCurrentAndStartNewPage("Activity Results Continued")
          drawTableHeader()
        }
        if (idx % 2 == 1) {
          canvas.drawRect(MARGIN_X, y, PAGE_WIDTH - MARGIN_X, y + rowHeight, rowAltPaint)
        }
        canvas.drawRect(MARGIN_X, y, PAGE_WIDTH - MARGIN_X, y + rowHeight, borderPaint)

        canvas.drawText("${row.worksheetId}", MARGIN_X + 6f, y + 13f, boldPaint)
        canvas.drawText(ellipsize(row.title, 36), MARGIN_X + 34f, y + 13f, bodyPaint)
        canvas.drawText(row.scoreText, MARGIN_X + 240f, y + 13f, bodyPaint)

        val stPaint = when (row.status) {
          ActivityMasteryStatus.PERFECT -> boldPaint
          ActivityMasteryStatus.PASSED -> greenPaint
          ActivityMasteryStatus.NOT_PASSED -> bluePaint
          ActivityMasteryStatus.NOT_COMPLETED -> amberPaint
          ActivityMasteryStatus.NOT_STARTED -> alertPaint
        }
        canvas.drawText(row.statusLabel, MARGIN_X + 318f, y + 13f, stPaint)

        val diffPaint = if (row.repeatedStillWrongItems.isNotEmpty()) alertPaint else mutedPaint
        if (diffText.length <= 26) {
          canvas.drawText(diffText, MARGIN_X + 392f, y + 13f, diffPaint)
        } else {
          val line1 = diffText.take(25)
          val line2 = ellipsize(diffText.drop(25).trimStart(',', ' '), 25)
          canvas.drawText(line1, MARGIN_X + 392f, y + 10.5f, diffPaint)
          canvas.drawText(line2, MARGIN_X + 392f, y + 21f, diffPaint)
        }
        y += rowHeight
      }
    }

    canvas.drawText(
      "ARAL MATH for BAGACAY ES • ${report.learnerName} • Legend: #N(2x!) = tried >1x & still wrong • Page $pageNumber",
      MARGIN_X,
      PAGE_HEIGHT - 18f,
      mutedPaint,
    )
    pdf.finishPage(page)
  }

  fun summarizeProblem(problem: MathProblem): String = when (problem) {
    is MathProblem.OddEvenProblem -> "Odd or Even: ${problem.number}"
    is MathProblem.CompareFractionsProblem -> "Compare ${problem.leftLabel} vs ${problem.rightLabel}"
    is MathProblem.OrderFractionsProblem -> "Order ${problem.fractions.joinToString(", ") { it.label }}"
    is MathProblem.FractionPartProblem -> "Fraction part of ${problem.num}/${problem.den}"
    is MathProblem.ShadedFigureFractionProblem -> problem.prompt
    is MathProblem.PictureFractionProblem -> problem.prompt
    is MathProblem.FractionWordsProblem -> "${problem.num}/${problem.den} in words"
    is MathProblem.FractionBlankProblem -> "Complete fraction ${problem.num}/${problem.den}"
    is MathProblem.PatternProblem -> "Pattern sequence (Item #${problem.itemNumber})"
    is MathProblem.ArithmeticProblem -> problem.promptText ?: problem.operands.joinToString(" ${problem.op.symbol} ")
    is MathProblem.GroupingDivisionProblem -> problem.prompt
    is MathProblem.DivisionPartProblem -> problem.prompt
    is MathProblem.DivisionProblem -> problem.promptText ?: "${problem.dividend} ÷ ${problem.divisor}"
    is MathProblem.GeometryIdentifyProblem -> problem.prompt
    is MathProblem.GeometryBoxFindProblem -> "Find ${problem.categoryLabel}"
    is MathProblem.GeometryDrawProblem -> problem.prompt
    is MathProblem.GeometryNameLinesProblem -> problem.prompt
    is MathProblem.CongruentSegmentsProblem -> problem.prompt
    is MathProblem.NumberDiscsReadProblem -> "Read number discs (answer space)"
    is MathProblem.NumberDiscsBuildProblem -> "Build ${problem.targetNumber} with discs"
    is MathProblem.PlaceValueProblem -> problem.promptText
    is MathProblem.NumberWordsProblem -> if (problem.toWords) "Write ${problem.number} in words" else problem.words
    is MathProblem.CompareWholeNumbersProblem -> "Compare ${problem.leftDisplay} vs ${problem.rightDisplay}"
    is MathProblem.RoundingProblem -> "Round ${problem.number} to ${problem.targetPlace}"
    is MathProblem.OrderNumbersProblem -> "Order ${problem.numbers.joinToString(", ")}"
    is MathProblem.EstimateOpProblem -> problem.promptText ?: "Estimate ${problem.factors.joinToString(" × ")}"
    is MathProblem.MentalMultiplyProblem -> "Mental math: ${problem.factorA} × ${problem.factorB}"
    is MathProblem.CompatibleEstimateProblem -> problem.promptText ?: "Estimate ${problem.dividend} ÷ ${problem.divisor}"
    is MathProblem.WordProblemMultiFieldProblem -> problem.promptText
    is MathProblem.OrderOfOpsProblem -> problem.promptText ?: "Evaluate: ${problem.expression}"
    is MathProblem.AngleNameProblem -> problem.prompt
    is MathProblem.AngleKindProblem -> problem.prompt
    is DivisibilityCheckProblem -> "Divisibility of ${problem.number}"
    is SmallestDigitDivisibilityProblem -> "Smallest digit in ${problem.templatePattern} ÷ ${problem.divisor}"
    is DivisibilityRuleJudgeProblem -> "Divisibility judge: ${problem.firstNumber} & ${problem.secondNumber}"
    is GcfLcmProblem -> problem.promptText ?: "GCF/LCM of ${problem.numbers.joinToString(", ")}"
    is FractionOpProblem -> problem.promptText ?: problem.expressionDisplay
    is PolygonTableProblem -> "Polygon (${problem.polygonSides} sides)"
    is PolygonRegularProblem -> "Regular vs Irregular polygon #${problem.polygonId}"
    is TimeConvertProblem -> "${problem.prompt}: ${problem.sourceDisplay}"
    is GeometryMeasureProblem -> problem.promptText
    is ThermometerProblem -> "Thermometer (${problem.mode.name.lowercase().replace('_', ' ')})"
    is CountObjectsProblem -> problem.prompt
    is StampObjectsProblem -> problem.prompt
    is SkipCountGridProblem -> problem.title
    is OrdinalShapeProblem -> problem.prompt
    is EqualPartsPartitionProblem -> "Equal parts of ${problem.shape.name.lowercase()} (${problem.expectedFractionLabel})"
    is InteractiveFractionProblem -> problem.prompt
    is VisualAddSubSentenceProblem -> problem.prompt
    is EquationPartProblem -> "Parts of ${problem.minuend} - ${problem.subtrahend} = ${problem.difference}"
    is ShapeClassificationProblem -> problem.prompt
    is CalendarProblem -> problem.prompt
    is ExpandedFormProblem -> "Expanded form of ${problem.number}"
    is CompareWordProblem -> problem.prompt
    is MultDivSentenceProblem -> problem.prompt
    is LineSurfaceProblem -> problem.prompt
  }

  private fun ellipsize(text: String, maxChars: Int): String {
    val clean = text.replace(Regex("\\s+"), " ").trim()
    return if (clean.length <= maxChars) clean else clean.take((maxChars - 1).coerceAtLeast(1)) + "…"
  }

  fun currentReportTimestamp(): String =
    SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US).format(Date())
}
