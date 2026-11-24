package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SheetCoordinateHelper
import com.example.data.WorksheetCatalog
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
import com.example.model.LineSurfaceProblem
import com.example.model.MathProblem
import com.example.model.MiniLesson
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
import com.example.ui.components.ConfettiOverlay
import com.example.ui.components.ScratchpadDialog
import com.example.ui.theme.*
import com.example.viewmodel.ActiveWorksheetUiState
import com.example.viewmodel.AnswerCheckResult

private val PerfectBlack = Color(0xFF14171F)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorksheetActivityScreen(
  state: ActiveWorksheetUiState,
  learnerName: String,
  activityStatuses: Map<Int, ActivityMasteryStatus>,
  remainingUnpassedCount: Int,
  onBack: () -> Unit,
  onJumpToIndex: (Int) -> Unit,
  onJumpToNextWrong: () -> Unit,
  onJumpToWorksheet: (Int) -> Unit,
  onNext: () -> Unit,
  onPrev: () -> Unit,
  onToggleHint: () -> Unit,
  onToggleMiniLesson: (Boolean?) -> Unit,
  onToggleScratchpad: (Boolean?) -> Unit,
  onRecordResult: (AnswerCheckResult) -> Unit,
  onDismissCertificate: () -> Unit,
  onRetryWorksheet: () -> Unit,
  onProceedNextActivity: () -> Unit,
  onGenerateProgressReport: () -> Unit = {},
) {
  BackHandler(onBack = onBack)

  val accent = state.worksheet.world.accentColor
  val jumperState = rememberLazyListState()
  var showActivitySwitcher by remember { mutableStateOf(false) }

  LaunchedEffect(state.currentIndex) {
    jumperState.animateScrollToItem((state.currentIndex - 2).coerceAtLeast(0))
  }

  Box(modifier = Modifier.fillMaxSize()) {
    Scaffold(
      containerColor = MaterialTheme.colorScheme.background,
      contentWindowInsets = WindowInsets.safeDrawing,
      topBar = {
        TopAppBar(
          title = {
            Column {
              Text(
                "WS ${state.worksheet.id}: ${state.worksheet.title}",
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
              Text(
                "${state.grade.shortLabel} • $learnerName • Score: ${state.currentPercent}%",
                style = MaterialTheme.typography.labelMedium,
                color = when {
                  state.isPerfect100 -> InkDark
                  state.isPassed90 -> LeafEmerald
                  else -> InkMuted
                },
              )
            }
          },
          navigationIcon = {
            IconButton(onClick = onBack, modifier = Modifier.testTag("back_home_button")) {
              Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to worksheets")
            }
          },
          actions = {
            IconButton(
              onClick = { showActivitySwitcher = true },
              modifier = Modifier.testTag("switch_activity_button"),
            ) {
              Icon(Icons.Default.Apps, contentDescription = "Jump to another activity", tint = BagacayBlue)
            }
            if (state.worksheet.miniLesson != null) {
              IconButton(onClick = { onToggleMiniLesson(true) }, modifier = Modifier.testTag("mini_lesson_btn")) {
                Icon(Icons.Default.MenuBook, contentDescription = "Mini Lesson", tint = accent)
              }
            }
            IconButton(onClick = { onToggleScratchpad(true) }, modifier = Modifier.testTag("scratchpad_btn")) {
              Icon(Icons.Default.EditNote, contentDescription = "Open Scratchpad", tint = BagacayBlue)
            }
          },
        )
      },
    ) { innerPadding ->
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding)
          .padding(horizontal = 16.dp)
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        // Live Score & Perfect Goal Strip
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Surface(
            color = when {
              state.isPerfect100 -> PerfectBlack
              state.isPassed90 -> LeafEmeraldLight
              else -> BagacayBlueLight.copy(alpha = 0.6f)
            },
            shape = RoundedCornerShape(12.dp),
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
              if (state.isPerfect100) {
                Icon(Icons.Default.Star, contentDescription = null, tint = MangoGold, modifier = Modifier.size(14.dp))
              }
              Text(
                text = when {
                  state.isPerfect100 -> "100% Perfect ★!"
                  state.isPassed90 -> "Passed 90%+! Aim for 100% ★"
                  else -> "Pass ≥90% (${state.requiredCorrectFor90}) • Perfect 100% ★"
                },
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = when {
                  state.isPerfect100 -> Color.White
                  state.isPassed90 -> LeafEmerald
                  else -> BagacayBlue
                },
              )
            }
          }

          Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
            if (state.streak >= 2) {
              Surface(color = MangoGoldLight, shape = RoundedCornerShape(12.dp)) {
                Text(
                  "🔥 ${state.streak}",
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                  fontWeight = FontWeight.Bold,
                  fontSize = 13.sp,
                )
              }
            }
            Surface(
              color = when {
                state.isPerfect100 -> PerfectBlack
                state.isPassed90 -> LeafEmeraldLight
                else -> WarmSurfaceVariant
              },
              shape = RoundedCornerShape(12.dp),
            ) {
              Text(
                "✓ ${state.solvedCorrectIndices.size}/${state.allProblems.size} (${state.currentPercent}%)",
                modifier = Modifier
                  .padding(horizontal = 10.dp, vertical = 4.dp)
                  .testTag("live_score_badge"),
                fontWeight = FontWeight.Bold,
                color = when {
                  state.isPerfect100 -> Color.White
                  state.isPassed90 -> LeafEmerald
                  else -> InkDark
                },
                fontSize = 13.sp,
              )
            }
          }
        }

        // Quick jump to Wrong Answers Banner if any wrong answers exist
        if (state.pendingWrongIndices.isNotEmpty()) {
          val firstPending = state.pendingWrongIndices.first()
          Surface(
            color = CoralLight,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
              .fillMaxWidth()
              .border(1.dp, CoralTangerine, RoundedCornerShape(14.dp))
              .clickable(onClick = onJumpToNextWrong)
              .testTag("jump_wrong_item_banner"),
          ) {
            Row(
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              Text(
                text = "Review ${state.pendingWrongIndices.size} wrong answer${if (state.pendingWrongIndices.size == 1) "" else "s"} (starts at #${firstPending + 1}) to reach 100% ★",
                style = MaterialTheme.typography.labelMedium,
                color = MangoDark,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
              )
              Text(
                text = "Go →",
                style = MaterialTheme.typography.labelMedium,
                color = CoralTangerine,
                fontWeight = FontWeight.Bold,
              )
            }
          }
        }

        // Progress bar toward 100% Perfect
        val progress = if (state.allProblems.isNotEmpty()) {
          state.solvedCorrectIndices.size.toFloat() / state.allProblems.size.toFloat()
        } else {
          0f
        }
        LinearProgressIndicator(
          progress = { progress.coerceIn(0f, 1f) },
          modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .clip(RoundedCornerShape(6.dp)),
          color = when {
            state.isPerfect100 -> PerfectBlack
            state.isPassed90 -> LeafEmerald
            else -> MangoGold
          },
          trackColor = OutlineSoft.copy(alpha = 0.5f),
        )

        // Scrollable Question Number Strip
        LazyRow(
          state = jumperState,
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.fillMaxWidth(),
        ) {
          itemsIndexed(state.allProblems) { idx, prob ->
            val isCurrent = idx == state.currentIndex
            val isRight = idx in state.solvedCorrectIndices
            val isWrong = idx in state.wrongAttemptIndices && !isRight
            val bg = when {
              isCurrent -> accent
              isRight -> LeafEmeraldLight
              isWrong -> CrimsonLight
              else -> WarmSurfaceVariant
            }
            val fg = when {
              isCurrent -> Color.White
              isRight -> LeafEmerald
              isWrong -> CrimsonRed
              else -> InkDark
            }
            Box(
              modifier = Modifier
                .defaultMinSize(minWidth = 38.dp, minHeight = 38.dp)
                .clip(CircleShape)
                .background(bg)
                .clickable { onJumpToIndex(idx) }
                .padding(horizontal = 10.dp),
              contentAlignment = Alignment.Center,
            ) {
              Text("${prob.itemNumber}", fontWeight = FontWeight.Bold, color = fg, fontSize = 13.sp)
            }
          }
        }

        // Main Interactive Problem Card
        val problem = state.currentProblem
        if (problem != null) {
          Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, OutlineSoft, RoundedCornerShape(24.dp))
                .padding(16.dp),
              verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
              ) {
                Surface(color = accent.copy(alpha = 0.14f), shape = RoundedCornerShape(10.dp)) {
                  Text(
                    "Item #${problem.itemNumber} of ${state.allProblems.size}",
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    color = accent,
                    fontWeight = FontWeight.Bold,
                  )
                }
                TextButton(onClick = onToggleHint, modifier = Modifier.testTag("hint_toggle")) {
                  Icon(Icons.Default.Lightbulb, contentDescription = null, tint = MangoGold, modifier = Modifier.size(18.dp))
                  Spacer(Modifier.width(4.dp))
                  Text(if (state.showHint) "Hide Hint" else "Hint")
                }
              }

              AnimatedVisibility(visible = state.showHint) {
                Surface(color = MangoGoldLight.copy(alpha = 0.55f), shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
                  Text(
                    text = "💡 ${problem.hint}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkDark,
                    modifier = Modifier.padding(10.dp),
                  )
                }
              }

              ProblemBodyDispatcher(
                problem = problem,
                accentColor = accent,
                onResult = onRecordResult,
              )
            }
          }
        }

        // Instant Feedback Banner (never reveals correct answer on wrong attempts)
        AnimatedVisibility(visible = state.feedback != null) {
          val fb = state.feedback
          if (fb != null) {
            val isSecondChance = !fb.isCorrect && state.awaitingSecondTry
            val bannerBg = when {
              fb.isCorrect -> LeafEmeraldLight
              isSecondChance -> MangoGoldLight
              else -> CrimsonLight
            }
            val bannerBorder = when {
              fb.isCorrect -> LeafEmerald
              isSecondChance -> MangoDark
              else -> CrimsonRed
            }
            val bannerTitleColor = when {
              fb.isCorrect -> LeafEmerald
              isSecondChance -> MangoDark
              else -> CrimsonRed
            }
            val problemHint = state.currentProblem?.hint
            Surface(
              color = bannerBg,
              shape = RoundedCornerShape(18.dp),
              modifier = Modifier
                .fillMaxWidth()
                .border(1.5.dp, bannerBorder, RoundedCornerShape(18.dp))
                .testTag("feedback_banner"),
            ) {
              Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
              ) {
                Column(
                  modifier = Modifier.weight(1f),
                  verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                  Text(
                    text = when {
                      fb.isCorrect -> "🎉 ${fb.message}"
                      isSecondChance -> "💡 Try 1 of 2 — Not quite! Try one more time."
                      else -> "💪 Try 2 of 2 — That's okay! Move to the next question and return later."
                    },
                    fontWeight = FontWeight.Bold,
                    color = bannerTitleColor,
                  )
                  if (!fb.isCorrect) {
                    Text(
                      text = fb.message,
                      style = MaterialTheme.typography.bodyMedium,
                      color = InkDark,
                    )
                    if (!problemHint.isNullOrBlank()) {
                      Text(
                        text = "Hint: $problemHint",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        color = InkDark,
                        modifier = Modifier.testTag("feedback_hint_text"),
                      )
                    }
                  }
                }
                if (fb.isCorrect || state.canAdvanceFromCurrent) {
                  Button(
                    onClick = onNext,
                    colors = ButtonDefaults.buttonColors(
                      containerColor = if (fb.isCorrect) LeafEmerald else BagacayBlue,
                    ),
                    modifier = Modifier
                      .defaultMinSize(minHeight = 48.dp)
                      .testTag("feedback_next_button"),
                  ) {
                    Text(if (state.currentIndex == state.allProblems.lastIndex) "Finish!" else "Next")
                    Spacer(Modifier.width(4.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                  }
                }
              }
            }
          }
        }

        // Bottom Prev / Next navigation
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          OutlinedButton(
            onClick = onPrev,
            enabled = state.currentIndex > 0,
            modifier = Modifier
              .weight(1f)
              .height(48.dp),
            shape = RoundedCornerShape(16.dp),
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(4.dp))
            Text("Previous")
          }
          OutlinedButton(
            onClick = onNext,
            enabled = state.canAdvanceFromCurrent,
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .testTag("bottom_next_button"),
            shape = RoundedCornerShape(16.dp),
          ) {
            Text(
              text = when {
                state.awaitingSecondTry -> "Try 2nd Attempt"
                state.currentIndex == state.allProblems.lastIndex -> "Finish Activity"
                else -> "Skip / Next"
              },
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
            )
            if (state.canAdvanceFromCurrent) {
              Spacer(Modifier.width(4.dp))
              Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
          }
        }
      }
    }

    ConfettiOverlay(trigger = state.confettiTrigger)
  }

  if (state.showMiniLesson && state.worksheet.miniLesson != null) {
    MiniLessonDialog(lesson = state.worksheet.miniLesson, onDismiss = { onToggleMiniLesson(false) })
  }

  if (state.showScratchpad) {
    ScratchpadDialog(onDismiss = { onToggleScratchpad(false) })
  }

  if (showActivitySwitcher) {
    ActivityJumpDialog(
      grade = state.grade,
      currentWorksheetId = state.worksheet.id,
      activityStatuses = activityStatuses,
      onSelectWorksheet = { id ->
        showActivitySwitcher = false
        onJumpToWorksheet(id)
      },
      onDismiss = { showActivitySwitcher = false },
    )
  }

  if (state.showCertificate) {
    StarCertificateDialog(
      grade = state.grade,
      worksheetId = state.worksheet.id,
      worksheetTitle = state.worksheet.title,
      solved = state.solvedCorrectIndices.size,
      total = state.allProblems.size,
      pendingWrongCount = state.pendingWrongIndices.size,
      remainingUnpassedCount = remainingUnpassedCount,
      onRetry = onRetryWorksheet,
      onProceedNext = onProceedNextActivity,
      onGenerateProgressReport = onGenerateProgressReport,
      onHome = {
        onDismissCertificate()
        onBack()
      },
    )
  }
}

@Composable
private fun ActivityJumpDialog(
  grade: AralGrade,
  currentWorksheetId: Int,
  activityStatuses: Map<Int, ActivityMasteryStatus>,
  onSelectWorksheet: (Int) -> Unit,
  onDismiss: () -> Unit,
) {
  val gradeWorksheets = remember(grade) { WorksheetCatalog.getWorksheets(grade) }
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Jump to Any ${grade.shortLabel} Activity (1–${grade.totalWorksheets})", style = MaterialTheme.typography.titleLarge) },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
          text = "Red: Not Started • Orange: Not Completed • Blue: Not Passed • Green: Passed • Black ★: 100% Perfect",
          style = MaterialTheme.typography.bodySmall,
          color = InkMuted,
        )
        LazyVerticalGrid(
          columns = GridCells.Fixed(5),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp),
          modifier = Modifier.heightIn(max = 320.dp),
        ) {
          items(gradeWorksheets) { ws ->
            val st = activityStatuses[ws.id] ?: ActivityMasteryStatus.NOT_STARTED
            val bg = when (st) {
              ActivityMasteryStatus.NOT_STARTED -> CrimsonLight
              ActivityMasteryStatus.NOT_COMPLETED -> CoralLight
              ActivityMasteryStatus.NOT_PASSED -> BagacayBlueLight
              ActivityMasteryStatus.PASSED -> LeafEmeraldLight
              ActivityMasteryStatus.PERFECT -> PerfectBlack
            }
            val fg = when (st) {
              ActivityMasteryStatus.NOT_STARTED -> CrimsonRed
              ActivityMasteryStatus.NOT_COMPLETED -> MangoDark
              ActivityMasteryStatus.NOT_PASSED -> BagacayBlue
              ActivityMasteryStatus.PASSED -> LeafEmerald
              ActivityMasteryStatus.PERFECT -> Color.White
            }
            Box(
              modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp))
                .background(bg)
                .border(
                  width = if (ws.id == currentWorksheetId) 2.5.dp else 1.dp,
                  color = if (ws.id == currentWorksheetId) MangoGold else fg.copy(alpha = 0.5f),
                  shape = RoundedCornerShape(12.dp),
                )
                .clickable { onSelectWorksheet(ws.id) },
              contentAlignment = Alignment.Center,
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (st == ActivityMasteryStatus.PERFECT) {
                  Icon(Icons.Default.Star, contentDescription = null, tint = MangoGold, modifier = Modifier.size(11.dp))
                }
                Text("${ws.id}", fontWeight = FontWeight.Bold, color = fg, fontSize = 14.sp)
              }
            }
          }
        }
      }
    },
    confirmButton = {
      TextButton(onClick = onDismiss) { Text("Close") }
    },
  )
}

@Composable
private fun ProblemBodyDispatcher(
  problem: MathProblem,
  accentColor: Color,
  onResult: (AnswerCheckResult) -> Unit,
) {
  when (problem) {
    is MathProblem.OddEvenProblem -> OddEvenRenderer(problem, accentColor, onResult)
    is MathProblem.CompareFractionsProblem -> CompareFractionsRenderer(problem, onResult)
    is MathProblem.OrderFractionsProblem -> OrderFractionsRenderer(problem, onResult)
    is MathProblem.FractionPartProblem -> FractionPartRenderer(problem, onResult)
    is MathProblem.ShadedFigureFractionProblem -> ShadedFigureRenderer(problem, onResult)
    is MathProblem.PictureFractionProblem -> PictureFractionRenderer(problem, onResult)
    is MathProblem.FractionWordsProblem -> FractionWordsRenderer(problem, onResult)
    is MathProblem.FractionBlankProblem -> FractionBlankRenderer(problem, onResult)
    is MathProblem.PatternProblem -> PatternRenderer(problem, onResult)
    is MathProblem.ArithmeticProblem -> ArithmeticRenderer(problem, onResult)
    is MathProblem.GroupingDivisionProblem -> GroupingDivisionRenderer(problem, onResult)
    is MathProblem.DivisionPartProblem -> DivisionPartRenderer(problem, onResult)
    is MathProblem.DivisionProblem -> DivisionRenderer(problem, onResult)
    is MathProblem.GeometryIdentifyProblem -> GeometryIdentifyRenderer(problem, onResult)
    is MathProblem.GeometryBoxFindProblem -> GeometryBoxFindRenderer(problem, onResult)
    is MathProblem.GeometryDrawProblem -> GeometryDrawRenderer(problem, onResult)
    is MathProblem.GeometryNameLinesProblem -> GeometryNameLinesRenderer(problem, onResult)
    is MathProblem.CongruentSegmentsProblem -> CongruentSegmentsRenderer(problem, onResult)
    is MathProblem.NumberDiscsReadProblem -> NumberDiscsReadRenderer(problem, onResult)
    is MathProblem.NumberDiscsBuildProblem -> NumberDiscsBuildRenderer(problem, onResult)
    is MathProblem.PlaceValueProblem -> PlaceValueRenderer(problem, onResult)
    is MathProblem.NumberWordsProblem -> NumberWordsRenderer(problem, onResult)
    is MathProblem.CompareWholeNumbersProblem -> CompareWholeNumbersRenderer(problem, onResult)
    is MathProblem.RoundingProblem -> RoundingRenderer(problem, onResult)
    is MathProblem.OrderNumbersProblem -> OrderNumbersRenderer(problem, onResult)
    // Grade 5 problem renderers
    is MathProblem.EstimateOpProblem -> EstimateOpRenderer(problem, onResult)
    is MathProblem.MentalMultiplyProblem -> MentalMultiplyRenderer(problem, onResult)
    is MathProblem.CompatibleEstimateProblem -> CompatibleEstimateRenderer(problem, onResult)
    is MathProblem.WordProblemMultiFieldProblem -> WordProblemMultiFieldRenderer(problem, onResult)
    is MathProblem.OrderOfOpsProblem -> OrderOfOpsRenderer(problem, onResult)
    is MathProblem.AngleNameProblem -> AngleNameRenderer(problem, onResult)
    is MathProblem.AngleKindProblem -> AngleKindRenderer(problem, onResult)
    // Grade 6 problem renderers
    is DivisibilityCheckProblem -> DivisibilityCheckRenderer(problem, onResult)
    is SmallestDigitDivisibilityProblem -> SmallestDigitDivisibilityRenderer(problem, onResult)
    is DivisibilityRuleJudgeProblem -> DivisibilityRuleJudgeRenderer(problem, onResult)
    is GcfLcmProblem -> GcfLcmRenderer(problem, onResult)
    is FractionOpProblem -> FractionOpRenderer(problem, onResult)
    is PolygonTableProblem -> PolygonTableRenderer(problem, onResult)
    is PolygonRegularProblem -> PolygonRegularRenderer(problem, onResult)
    is TimeConvertProblem -> TimeConvertRenderer(problem, onResult)
    is GeometryMeasureProblem -> GeometryMeasureRenderer(problem, onResult)
    is ThermometerProblem -> ThermometerRenderer(problem, onResult)
    // Grade 2 & Grade 3 problem renderers
    is CountObjectsProblem -> CountObjectsRenderer(problem, onResult)
    is StampObjectsProblem -> StampObjectsRenderer(problem, onResult)
    is SkipCountGridProblem -> SkipCountGridRenderer(problem, onResult)
    is OrdinalShapeProblem -> OrdinalShapeRenderer(problem, onResult)
    is EqualPartsPartitionProblem -> EqualPartsPartitionRenderer(problem, onResult)
    is InteractiveFractionProblem -> InteractiveFractionRenderer(problem, onResult)
    is VisualAddSubSentenceProblem -> VisualAddSubSentenceRenderer(problem, onResult)
    is EquationPartProblem -> EquationPartRenderer(problem, onResult)
    is ShapeClassificationProblem -> ShapeClassificationRenderer(problem, onResult)
    is CalendarProblem -> CalendarRenderer(problem, onResult)
    is ExpandedFormProblem -> ExpandedFormRenderer(problem, onResult)
    is CompareWordProblem -> CompareWordRenderer(problem, onResult)
    is MultDivSentenceProblem -> MultDivSentenceRenderer(problem, onResult)
    is LineSurfaceProblem -> LineSurfaceRenderer(problem, onResult)
  }
}

@Composable
private fun MiniLessonDialog(lesson: MiniLesson, onDismiss: () -> Unit) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("📘 ${lesson.heading}", style = MaterialTheme.typography.headlineSmall) },
    text = {
      Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        lesson.rules.forEach { rule ->
          Text("• $rule", style = MaterialTheme.typography.bodyLarge)
        }
        Surface(color = WarmSurfaceVariant, shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth()) {
          Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(lesson.exampleTitle, fontWeight = FontWeight.Bold, color = BagacayBlue)
            Text(lesson.exampleBody, style = MaterialTheme.typography.bodyMedium)
          }
        }
      }
    },
    confirmButton = {
      Button(onClick = onDismiss) { Text("Got It!") }
    },
  )
}

@Composable
private fun StarCertificateDialog(
  grade: AralGrade,
  worksheetId: Int,
  worksheetTitle: String,
  solved: Int,
  total: Int,
  pendingWrongCount: Int,
  remainingUnpassedCount: Int,
  onRetry: () -> Unit,
  onProceedNext: () -> Unit,
  onGenerateProgressReport: () -> Unit,
  onHome: () -> Unit,
) {
  val pct = SheetCoordinateHelper.computePercent(solved, total)
  val isPassed90 = SheetCoordinateHelper.hasPassedMastery(solved, total)
  val isPerfect100 = total > 0 && solved >= total
  val hasNextActivity = worksheetId < grade.totalWorksheets
  val stars = when {
    pct >= 90 -> 3
    pct >= 50 -> 2
    solved > 0 -> 1
    else -> 0
  }

  AlertDialog(
    onDismissRequest = onHome,
    title = {
      Text(
        text = when {
          isPerfect100 -> "★ 100% Perfect Score Award!"
          isPassed90 -> "Bagacay ES Activity Passed!"
          else -> "Keep Going, Explorer!"
        },
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
      )
    },
    text = {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          for (s in 1..3) {
            Icon(
              Icons.Default.Star,
              contentDescription = null,
              tint = if (s <= stars) MangoGold else OutlineSoft,
              modifier = Modifier.size(42.dp),
            )
          }
        }
        Text("WS $worksheetId: $worksheetTitle", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
        Text(
          text = "Score: $pct% ($solved out of $total correct)",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = when {
            isPerfect100 -> InkDark
            isPassed90 -> LeafEmerald
            else -> BagacayBlue
          },
          textAlign = TextAlign.Center,
        )

        Surface(
          color = when {
            isPerfect100 -> PerfectBlack
            isPassed90 -> LeafEmeraldLight
            else -> BagacayBlueLight
          },
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(
              1.dp,
              when {
                isPerfect100 -> MangoGold
                isPassed90 -> LeafEmerald
                else -> BagacayBlue
              },
              RoundedCornerShape(14.dp),
            ),
        ) {
          Text(
            text = when {
              isPerfect100 ->
                "★ Incredible! You got a 100% Perfect Score and earned the Black ★ Star badge!"
              isPassed90 && pendingWrongCount > 0 ->
                "Great pass! You can return to your $pendingWrongCount wrong item(s) now to reach 100% ★!"
              isPassed90 ->
                "Great pass! Only ${total - solved} item(s) left to reach 100% Perfect and earn the Black ★ badge!"
              pendingWrongCount > 0 ->
                "Not passed yet (need ≥ 90%). Return to your $pendingWrongCount wrong item(s) now to try again with hints, or jump to another activity anytime."
              else ->
                "Not passed yet (need ≥ 90%). You can jump to another activity anytime, or fix your remaining items now to aim for 100% ★!"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = if (isPerfect100) Color.White else InkDark,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(10.dp),
          )
        }

        if (remainingUnpassedCount > 0) {
          Text(
            text = "Reminder: $remainingUnpassedCount activity(ies) still need to be answered or passed.",
            style = MaterialTheme.typography.labelMedium,
            color = CoralTangerine,
            textAlign = TextAlign.Center,
          )
        }
      }
    },
    confirmButton = {
      Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
      ) {
        if (hasNextActivity) {
          Button(
            onClick = onProceedNext,
            colors = ButtonDefaults.buttonColors(containerColor = BagacayBlue),
            modifier = Modifier
              .fillMaxWidth()
              .defaultMinSize(minHeight = 48.dp)
              .testTag("proceed_next_activity_button"),
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(6.dp))
            Text("Jump to Activity ${worksheetId + 1}")
          }
        }
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          if (!isPerfect100) {
            OutlinedButton(
              onClick = onRetry,
              modifier = Modifier
                .weight(1f)
                .defaultMinSize(minHeight = 48.dp)
                .testTag("retry_wrong_button"),
            ) {
              Text(
                if (pendingWrongCount > 0) "Return to Wrong ($pendingWrongCount)" else "Fix Wrong",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
            }
          }
          Button(
            onClick = onHome,
            modifier = Modifier
              .weight(1f)
              .defaultMinSize(minHeight = 48.dp),
          ) {
            Text("All Activities")
          }
        }
        OutlinedButton(
          onClick = onGenerateProgressReport,
          modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .testTag("certificate_progress_report_button"),
        ) {
          Icon(Icons.Default.PictureAsPdf, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(Modifier.width(6.dp))
          Text("Send Progress Report (PDF)")
        }
      }
    },
  )
}
