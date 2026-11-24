package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.ProgressReportPdfGenerator
import com.example.data.WorksheetCatalog
import com.example.model.ActivityMasteryStatus
import com.example.model.ActivityRecord
import com.example.model.AralGrade
import com.example.model.LearningWorld
import com.example.model.WorksheetInfo
import com.example.ui.components.CircularProgressTracker
import com.example.ui.theme.*
import com.example.viewmodel.HomeUiState

private val PerfectBlack = Color(0xFF14171F)
private val PerfectBlackSoft = Color(0xFF252A36)

@Composable
fun HomeScreen(
  state: HomeUiState,
  onSelectGrade: (AralGrade) -> Unit,
  onSelectWorld: (LearningWorld?) -> Unit,
  onSearchChange: (String) -> Unit,
  onToggleHintsDefault: (Boolean) -> Unit,
  onOpenWorksheet: (Int, Int?) -> Unit,
  onSwitchLearner: () -> Unit,
  onGenerateProgressReport: () -> Unit,
  onDismissBanner: () -> Unit,
) {
  val context = LocalContext.current
  val learner = state.activeLearner
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(state.statusBannerMessage) {
    val msg = state.statusBannerMessage
    if (!msg.isNullOrBlank()) {
      snackbarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
      onDismissBanner()
    }
  }

  Scaffold(
    containerColor = MaterialTheme.colorScheme.background,
    contentWindowInsets = WindowInsets.safeDrawing,
    snackbarHost = { SnackbarHost(snackbarHostState) },
  ) { innerPadding ->
    LazyVerticalGrid(
      columns = GridCells.Adaptive(minSize = 310.dp),
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 28.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      item(span = { GridItemSpan(maxLineSpan) }) {
        HeroProgressBanner(
          state = state,
          onSelectGrade = onSelectGrade,
          onToggleHintsDefault = onToggleHintsDefault,
          onSwitchLearner = onSwitchLearner,
          onGenerateProgressReport = onGenerateProgressReport,
        )
      }

      item(span = { GridItemSpan(maxLineSpan) }) {
        ProgressReportActionCard(
          state = state,
          onGenerateProgressReport = onGenerateProgressReport,
          onViewLastPdf = {
            val pdfFile = state.lastGeneratedPdfFile
            if (pdfFile != null && !ProgressReportPdfGenerator.openPdfFile(context, pdfFile)) {
              Toast.makeText(context, "No PDF viewer app installed on this device.", Toast.LENGTH_SHORT).show()
            }
          },
        )
      }

      if (learner != null && state.currentGrade.hasActivities) {
        item(span = { GridItemSpan(maxLineSpan) }) {
          val grade = state.currentGrade
          val (resumeWs, resumeItem) = learner.resumeTarget(grade)
          val hasWrong = (learner.recordsFor(grade)[resumeWs]?.pendingWrongIndices?.isNotEmpty() == true)
          ContinueBanner(
            grade = grade,
            worksheetId = resumeWs,
            itemIndex = resumeItem,
            hasWrongAnswers = hasWrong,
            onContinue = { onOpenWorksheet(resumeWs, resumeItem) },
          )
        }
      }

      if (state.currentGrade.hasActivities) {
        item(span = { GridItemSpan(maxLineSpan) }) {
          ActivityReminderAndLegendCard(
            state = state,
            onJumpToWorksheet = { id -> onOpenWorksheet(id, null) },
          )
        }
      }

      if (!state.currentGrade.hasActivities) {
        item(span = { GridItemSpan(maxLineSpan) }) {
          GradeComingSoonCard(
            grade = state.currentGrade,
            onSwitchToGrade = onSelectGrade,
          )
        }
      } else {
        item(span = { GridItemSpan(maxLineSpan) }) {
          OutlinedTextField(
            value = state.searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Search worksheet 1–${state.totalActivityCount} or topic (e.g. fractions, division)...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            trailingIcon = {
              if (state.searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchChange("") }) {
                  Icon(Icons.Default.Clear, contentDescription = "Clear search")
                }
              }
            },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("search_worksheets"),
          )
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
          val gradeWorlds = remember(state.currentGrade) { LearningWorld.forGrade(state.currentGrade) }
          LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 2.dp),
          ) {
            item {
              FilterChip(
                selected = state.selectedWorld == null,
                onClick = { onSelectWorld(null) },
                label = { Text("All ${state.totalActivityCount} Worksheets", fontWeight = FontWeight.Bold) },
                leadingIcon = { Icon(Icons.Default.Apps, contentDescription = null, modifier = Modifier.size(18.dp)) },
              )
            }
            items(gradeWorlds) { world ->
              FilterChip(
                selected = state.selectedWorld == world,
                onClick = { onSelectWorld(world) },
                label = { Text(world.title, fontWeight = FontWeight.Bold) },
                colors = FilterChipDefaults.filterChipColors(
                  selectedContainerColor = world.containerColor,
                  selectedLabelColor = InkDark,
                ),
              )
            }
          }
        }

        if (state.filteredWorksheets.isEmpty()) {
          item(span = { GridItemSpan(maxLineSpan) }) {
            Surface(
              color = WarmSurfaceVariant,
              shape = RoundedCornerShape(24.dp),
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            ) {
              Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
              ) {
                Icon(Icons.Default.SearchOff, contentDescription = null, tint = InkMuted, modifier = Modifier.size(44.dp))
                Text("No worksheets matched your search!", style = MaterialTheme.typography.titleLarge)
                TextButton(onClick = { onSearchChange(""); onSelectWorld(null) }) {
                  Text("Show All ${state.totalActivityCount} Worksheets")
                }
              }
            }
          }
        } else {
          items(state.filteredWorksheets, key = { it.id }) { ws ->
            val status = state.statusFor(ws.id)
            WorksheetCard(
              worksheet = ws,
              record = state.scores[ws.id],
              status = status,
              onClick = { onOpenWorksheet(ws.id, null) },
            )
          }
        }
      }

      item(span = { GridItemSpan(maxLineSpan) }) {
        Surface(
          color = WarmSurfaceVariant,
          shape = RoundedCornerShape(18.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, OutlineSoft, RoundedCornerShape(18.dp)),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
          ) {
            Image(
              painter = painterResource(id = R.drawable.bagacay_school_logo),
              contentDescription = stringResource(R.string.school_logo_desc),
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(Color.White),
            )
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "Bagacay Elementary School • District of Culasi",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = InkDark,
              )
              Text(
                text = stringResource(R.string.innovation_credit),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = BagacayBlue,
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun HeroProgressBanner(
  state: HomeUiState,
  onSelectGrade: (AralGrade) -> Unit,
  onToggleHintsDefault: (Boolean) -> Unit,
  onSwitchLearner: () -> Unit,
  onGenerateProgressReport: () -> Unit,
) {
  val learner = state.activeLearner
  val showHintsDefault = state.settings.showHintsByDefault

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(28.dp))
      .background(
        Brush.linearGradient(
          colors = listOf(Color(0xFF1565C0), Color(0xFF0288D1), Color(0xFF26A69A)),
        ),
      )
      .padding(18.dp),
  ) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Row(
          modifier = Modifier.weight(1f),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
          CircularProgressTracker(
            completedCount = state.completedCount,
            totalCount = state.totalActivityCount,
            size = 96.dp,
            strokeWidth = 10.dp,
          )
          Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
              text = "${stringResource(R.string.hero_title)} • ${state.currentGrade.shortLabel}",
              style = MaterialTheme.typography.titleLarge,
              color = Color.White,
            )
            if (learner != null) {
              Text(
                text = learner.profile.name,
                style = MaterialTheme.typography.titleMedium,
                color = MangoGoldLight,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
              Text(
                text = "${learner.profile.gradeLevel} • ${learner.profile.school}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
              Text(
                text = stringResource(R.string.innovation_credit),
                style = MaterialTheme.typography.labelSmall,
                color = MangoGoldLight,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
            } else {
              Text(
                text = stringResource(R.string.hero_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.9f),
              )
              Text(
                text = stringResource(R.string.innovation_credit),
                style = MaterialTheme.typography.labelSmall,
                color = MangoGoldLight,
                fontWeight = FontWeight.SemiBold,
              )
            }
          }
        }
        Image(
          painter = painterResource(id = R.drawable.bagacay_school_logo),
          contentDescription = stringResource(R.string.school_logo_desc),
          contentScale = ContentScale.Crop,
          modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(Color.White)
            .border(2.dp, MangoGold, CircleShape),
        )
      }

      // ARAL Grade Program Switcher (2 / 3 / 4 / 5 / 6)
      Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        AralGrade.entries.chunked(3).forEach { rowGrades ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            rowGrades.forEach { prog ->
              val isSelected = state.currentGrade == prog
              val passedInGrade = learner?.completedCountFor(prog) ?: 0
              Surface(
                color = if (isSelected) MangoGold else Color.White.copy(alpha = 0.16f),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                  .weight(1f)
                  .clickable { onSelectGrade(prog) }
                  .testTag("grade_switcher_${prog.gradeNumber}"),
              ) {
                Column(
                  modifier = Modifier.padding(vertical = 7.dp, horizontal = 6.dp),
                  horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                  Text(
                    text = "ARAL ${prog.gradeNumber}",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) InkDark else Color.White,
                  )
                  Text(
                    text = if (prog.hasActivities) "$passedInGrade/${prog.totalWorksheets}" else "Soon",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isSelected) InkDark.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.85f),
                  )
                }
              }
            }
          }
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        StatPill(icon = Icons.Default.Star, tint = MangoGold, label = "${state.totalStars} Stars")
        StatPill(
          icon = Icons.Default.Verified,
          tint = MangoGoldLight,
          label = "${state.completedCount}/${state.totalActivityCount} Passed",
        )
        StatPill(
          icon = Icons.Default.AutoAwesome,
          tint = MangoGold,
          label = "${state.perfectCount} Perfect ★",
        )
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Surface(
          color = Color.White.copy(alpha = 0.2f),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .clickable(onClick = onSwitchLearner)
            .testTag("switch_learner_button"),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
          ) {
            Icon(Icons.Default.SwitchAccount, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
            Text(stringResource(R.string.switch_learner), color = Color.White, style = MaterialTheme.typography.labelMedium)
          }
        }

        Surface(
          color = Color.White.copy(alpha = 0.18f),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier.clickable { onToggleHintsDefault(!showHintsDefault) },
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            Icon(
              if (showHintsDefault) Icons.Default.Lightbulb else Icons.Outlined.Lightbulb,
              contentDescription = "Toggle hints by default",
              tint = MangoGoldLight,
              modifier = Modifier.size(16.dp),
            )
            Text(
              if (showHintsDefault) "Hints On" else "Hints Off",
              color = Color.White,
              style = MaterialTheme.typography.labelMedium,
            )
          }
        }

        Surface(
          color = MangoGold,
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .clickable(enabled = !state.isGeneratingReport, onClick = onGenerateProgressReport)
            .testTag("hero_progress_report_button"),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
          ) {
            Icon(Icons.Default.PictureAsPdf, contentDescription = null, tint = InkDark, modifier = Modifier.size(16.dp))
            Text(
              text = if (state.isGeneratingReport) "Creating..." else stringResource(R.string.progress_report),
              color = InkDark,
              fontWeight = FontWeight.Bold,
              style = MaterialTheme.typography.labelMedium,
            )
          }
        }
      }
    }
  }
}

@Composable
private fun ProgressReportActionCard(
  state: HomeUiState,
  onGenerateProgressReport: () -> Unit,
  onViewLastPdf: () -> Unit,
) {
  Surface(
    color = BagacayBlueLight.copy(alpha = 0.55f),
    shape = RoundedCornerShape(22.dp),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.5.dp, BagacayBlue.copy(alpha = 0.65f), RoundedCornerShape(22.dp)),
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        Box(
          modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(BagacayBlue),
          contentAlignment = Alignment.Center,
        ) {
          Icon(
            imageVector = Icons.Default.PictureAsPdf,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(22.dp),
          )
        }
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "${state.currentGrade.shortLabel} PDF Progress Report",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = InkDark,
          )
          Text(
            text = "Generates scores per activity plus items tried more than once with wrong answers, then opens Messenger with the PDF attached.",
            style = MaterialTheme.typography.bodySmall,
            color = InkMuted,
          )
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Surface(
          color = Color.White,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.border(1.dp, OutlineSoft, RoundedCornerShape(10.dp)),
        ) {
          Text(
            text = "Attempted: ${state.attemptedActivityCount}/${state.totalActivityCount}",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = BagacayBlue,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
          )
        }
        Surface(
          color = if (state.difficultyItemCount > 0) CrimsonLight else LeafEmeraldLight,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.border(
            1.dp,
            if (state.difficultyItemCount > 0) CrimsonRed.copy(alpha = 0.45f) else LeafEmerald.copy(alpha = 0.45f),
            RoundedCornerShape(10.dp),
          ),
        ) {
          Text(
            text = if (state.difficultyItemCount > 0) {
              "Difficulty Items Flagged: ${state.difficultyItemCount}"
            } else {
              "No Difficulty Items Flagged"
            },
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (state.difficultyItemCount > 0) CrimsonRed else LeafEmerald,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
          )
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Button(
          onClick = onGenerateProgressReport,
          enabled = !state.isGeneratingReport,
          colors = ButtonDefaults.buttonColors(containerColor = BagacayBlue),
          shape = RoundedCornerShape(14.dp),
          modifier = Modifier
            .weight(1f)
            .defaultMinSize(minHeight = 48.dp)
            .testTag("progress_report_button"),
        ) {
          if (state.isGeneratingReport) {
            CircularProgressIndicator(
              color = Color.White,
              strokeWidth = 2.dp,
              modifier = Modifier.size(18.dp),
            )
            Spacer(Modifier.width(8.dp))
            Text("Generating PDF...", style = MaterialTheme.typography.labelLarge)
          } else {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.send_report_messenger), style = MaterialTheme.typography.labelLarge)
          }
        }

        if (state.lastGeneratedPdfFile != null) {
          OutlinedButton(
            onClick = onViewLastPdf,
            modifier = Modifier
              .defaultMinSize(minHeight = 48.dp)
              .testTag("view_pdf_report_button"),
            shape = RoundedCornerShape(14.dp),
          ) {
            Icon(Icons.Default.Visibility, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Text("View PDF", style = MaterialTheme.typography.labelMedium)
          }
        }
      }
    }
  }
}

@Composable
private fun ActivityReminderAndLegendCard(
  state: HomeUiState,
  onJumpToWorksheet: (Int) -> Unit,
) {
  val needAttention = state.activitiesNeedingAttention
  Surface(
    color = MaterialTheme.colorScheme.surface,
    shape = RoundedCornerShape(22.dp),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(22.dp))
      .testTag("activity_reminder_card"),
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        Icon(
          imageVector = if (state.perfectCount == state.totalActivityCount) Icons.Default.EmojiEvents else Icons.Default.NotificationsActive,
          contentDescription = null,
          tint = if (needAttention.isNotEmpty()) CoralTangerine else MangoGold,
        )
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = when {
              state.perfectCount == state.totalActivityCount ->
                "★ All ${state.totalActivityCount} ${state.currentGrade.shortLabel} Activities 100% Perfect!"
              needAttention.isEmpty() ->
                "All ${state.totalActivityCount} Passed! Aim for 100% Perfect ★!"
              else ->
                "Reminder (${state.currentGrade.shortLabel}): ${needAttention.size} Activities Need Answering or Passing"
            },
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = InkDark,
          )
          Text(
            text = when {
              state.perfectCount == state.totalActivityCount ->
                "Every single activity has a Black ★ Perfect Score. Amazing work!"
              needAttention.isEmpty() ->
                "Jump to any Green activity to fix wrong answers and earn the Black ★ Perfect Score!"
              else ->
                "Jump freely to any activity below! Aim for 100% to earn the Black ★ Perfect Score badge."
            },
            style = MaterialTheme.typography.bodySmall,
            color = InkMuted,
          )
        }
      }

      // 5-Color Legend Chips
      LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        item {
          LegendChip(
            bg = CrimsonLight,
            dot = CrimsonRed,
            textColor = CrimsonRed,
            label = "Not Started (${state.notStartedCount})",
          )
        }
        item {
          LegendChip(
            bg = CoralLight,
            dot = CoralTangerine,
            textColor = MangoDark,
            label = "Not Completed (${state.notCompletedCount})",
          )
        }
        item {
          LegendChip(
            bg = BagacayBlueLight,
            dot = BagacayBlue,
            textColor = BagacayBlue,
            label = "Not Passed (${state.notPassedCount})",
          )
        }
        item {
          LegendChip(
            bg = LeafEmeraldLight,
            dot = LeafEmerald,
            textColor = LeafEmerald,
            label = "Passed (${state.passedOnlyCount})",
          )
        }
        item {
          Surface(color = PerfectBlack, shape = RoundedCornerShape(12.dp)) {
            Row(
              modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
              Icon(Icons.Default.Star, contentDescription = null, tint = MangoGold, modifier = Modifier.size(14.dp))
              Text(
                text = "Perfect (${state.perfectCount})",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color.White,
              )
            }
          }
        }
      }

      if (needAttention.isNotEmpty()) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
          items(needAttention.take(10)) { wsId ->
            val st = state.statusFor(wsId)
            val chipBg = when (st) {
              ActivityMasteryStatus.NOT_STARTED -> CrimsonLight
              ActivityMasteryStatus.NOT_COMPLETED -> CoralLight
              ActivityMasteryStatus.NOT_PASSED -> BagacayBlueLight
              else -> WarmSurfaceVariant
            }
            val chipFg = when (st) {
              ActivityMasteryStatus.NOT_STARTED -> CrimsonRed
              ActivityMasteryStatus.NOT_COMPLETED -> MangoDark
              ActivityMasteryStatus.NOT_PASSED -> BagacayBlue
              else -> InkDark
            }
            Surface(
              color = chipBg,
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .border(1.dp, chipFg.copy(alpha = 0.45f), RoundedCornerShape(12.dp))
                .clickable { onJumpToWorksheet(wsId) },
            ) {
              Text(
                text = "WS $wsId • ${st.label}",
                color = chipFg,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun LegendChip(bg: Color, dot: Color, textColor: Color, label: String) {
  Surface(color = bg, shape = RoundedCornerShape(12.dp)) {
    Row(
      modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
      Box(
        modifier = Modifier
          .size(9.dp)
          .clip(CircleShape)
          .background(dot),
      )
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = textColor,
      )
    }
  }
}

@Composable
private fun ContinueBanner(
  grade: AralGrade,
  worksheetId: Int,
  itemIndex: Int,
  hasWrongAnswers: Boolean,
  onContinue: () -> Unit,
) {
  val ws = remember(grade, worksheetId) { WorksheetCatalog.getById(worksheetId, grade) }
  Surface(
    color = MangoGoldLight.copy(alpha = 0.72f),
    shape = RoundedCornerShape(20.dp),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.5.dp, MangoGold, RoundedCornerShape(20.dp))
      .clickable(onClick = onContinue)
      .testTag("continue_left_off_card"),
  ) {
    Row(
      modifier = Modifier.padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(MangoGold),
        contentAlignment = Alignment.Center,
      ) {
        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = InkDark)
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = if (hasWrongAnswers) "Fix Wrong Answers & Aim for 100% ★" else "Continue Where You Left Off",
          style = MaterialTheme.typography.labelLarge,
          fontWeight = FontWeight.Bold,
          color = MangoDark,
        )
        Text(
          text = "WS ${ws.id}: ${ws.title} (Item #${itemIndex + 1})",
          style = MaterialTheme.typography.bodyMedium,
          color = InkDark,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
      }
      Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Continue", tint = MangoDark)
    }
  }
}

@Composable
private fun StatPill(icon: androidx.compose.ui.graphics.vector.ImageVector, tint: Color, label: String) {
  Surface(color = Color.White.copy(alpha = 0.18f), shape = RoundedCornerShape(16.dp)) {
    Row(
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
      Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
      Text(label, color = Color.White, style = MaterialTheme.typography.labelMedium)
    }
  }
}

@Composable
private fun WorksheetCard(
  worksheet: WorksheetInfo,
  record: ActivityRecord?,
  status: ActivityMasteryStatus,
  onClick: () -> Unit,
) {
  val statusColor: Color
  val statusBg: Color
  val badgeTextColor: Color

  when (status) {
    ActivityMasteryStatus.NOT_STARTED -> {
      statusColor = CrimsonRed
      statusBg = CrimsonLight
      badgeTextColor = CrimsonRed
    }
    ActivityMasteryStatus.NOT_COMPLETED -> {
      statusColor = CoralTangerine
      statusBg = CoralLight
      badgeTextColor = MangoDark
    }
    ActivityMasteryStatus.NOT_PASSED -> {
      statusColor = BagacayBlue
      statusBg = BagacayBlueLight
      badgeTextColor = BagacayBlue
    }
    ActivityMasteryStatus.PASSED -> {
      statusColor = LeafEmerald
      statusBg = LeafEmeraldLight
      badgeTextColor = LeafEmerald
    }
    ActivityMasteryStatus.PERFECT -> {
      statusColor = PerfectBlack
      statusBg = PerfectBlack
      badgeTextColor = Color.White
    }
  }

  val cardContainer = if (status == ActivityMasteryStatus.PERFECT) {
    Color(0xFFFFFDF4)
  } else {
    MaterialTheme.colorScheme.surface
  }

  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("worksheet_card_${worksheet.id}")
      .clickable(onClick = onClick),
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = cardContainer),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .border(width = 2.dp, color = statusColor, shape = RoundedCornerShape(22.dp))
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      // Status-colored WS Number Badge (Black with Gold Star when 100% Perfect!)
      Box(
        modifier = Modifier
          .size(58.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(statusBg)
          .border(2.dp, if (status == ActivityMasteryStatus.PERFECT) MangoGold else statusColor, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,
      ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          if (status == ActivityMasteryStatus.PERFECT) {
            Icon(Icons.Default.Star, contentDescription = "Perfect Score", tint = MangoGold, modifier = Modifier.size(16.dp))
          } else {
            Text("WS", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = badgeTextColor)
          }
          Text(
            text = "${worksheet.id}",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = if (status == ActivityMasteryStatus.PERFECT) Color.White else InkDark,
          )
        }
      }

      Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
          text = worksheet.title,
          style = MaterialTheme.typography.titleLarge,
          color = InkDark,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
        Text(
          text = "${worksheet.world.title} • ${worksheet.itemCount} items",
          style = MaterialTheme.typography.bodyMedium,
          color = InkMuted,
        )

        // Status Pill
        Surface(
          color = statusBg,
          shape = RoundedCornerShape(10.dp),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            if (status == ActivityMasteryStatus.PERFECT) {
              Icon(Icons.Default.Star, contentDescription = null, tint = MangoGold, modifier = Modifier.size(14.dp))
            }
            val labelText = when (status) {
              ActivityMasteryStatus.NOT_STARTED -> "Not Started"
              ActivityMasteryStatus.NOT_COMPLETED ->
                "Not Completed (${record?.attemptedCount ?: 0}/${worksheet.itemCount} answered)"
              ActivityMasteryStatus.NOT_PASSED ->
                "Not Passed • ${record?.percent ?: 0}% (${record?.correctCount ?: 0}/${record?.totalItems ?: worksheet.itemCount})"
              ActivityMasteryStatus.PASSED ->
                "Passed • ${record?.percent ?: 0}% (${record?.correctCount ?: 0}/${record?.totalItems ?: worksheet.itemCount}) • Try for 100%!"
              ActivityMasteryStatus.PERFECT ->
                "Perfect Score 100% (${record?.correctCount ?: worksheet.itemCount}/${record?.totalItems ?: worksheet.itemCount})!"
            }
            Text(
              text = labelText,
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = badgeTextColor,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
            )
          }
        }

        // Stars + Wrong / Timestamp Info
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(3.dp)) {
          val stars = record?.stars ?: 0
          for (s in 1..3) {
            Icon(
              imageVector = if (s <= stars) Icons.Default.Star else Icons.Outlined.StarOutline,
              contentDescription = null,
              tint = if (s <= stars) MangoGold else OutlineSoft,
              modifier = Modifier.size(15.dp),
            )
          }
          val wrongCount = record?.pendingWrongIndices?.size ?: 0
          when {
            wrongCount > 0 -> {
              Spacer(Modifier.width(4.dp))
              Text(
                text = "Tap to fix $wrongCount wrong item${if (wrongCount == 1) "" else "s"}",
                style = MaterialTheme.typography.labelSmall,
                color = statusColor,
                fontWeight = FontWeight.SemiBold,
              )
            }
            record != null && record.timestamp.isNotBlank() -> {
              Spacer(Modifier.width(4.dp))
              Text(
                text = record.timestamp,
                style = MaterialTheme.typography.labelSmall,
                color = InkMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
              )
            }
          }
        }
      }

      FilledTonalIconButton(
        onClick = onClick,
        colors = IconButtonDefaults.filledTonalIconButtonColors(
          containerColor = if (status == ActivityMasteryStatus.PERFECT) PerfectBlackSoft else statusColor.copy(alpha = 0.14f),
          contentColor = if (status == ActivityMasteryStatus.PERFECT) MangoGold else statusColor,
        ),
      ) {
        Icon(
          imageVector = when (status) {
            ActivityMasteryStatus.PERFECT -> Icons.Default.Star
            ActivityMasteryStatus.PASSED -> Icons.Default.CheckCircle
            else -> Icons.Default.PlayArrow
          },
          contentDescription = "Open Worksheet ${worksheet.id}",
        )
      }
    }
  }
}

@Composable
private fun GradeComingSoonCard(
  grade: AralGrade,
  onSwitchToGrade: (AralGrade) -> Unit,
) {
  Surface(
    color = MaterialTheme.colorScheme.surface,
    shape = RoundedCornerShape(24.dp),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.5.dp, OutlineSoft, RoundedCornerShape(24.dp))
      .testTag("grade_coming_soon_card"),
  ) {
    Column(
      modifier = Modifier.padding(22.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      Icon(
        imageVector = Icons.Default.EventNote,
        contentDescription = null,
        tint = BagacayBlue,
        modifier = Modifier.size(44.dp),
      )
      Text(
        text = "${grade.displayTitle} — Activities Coming Soon",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = InkDark,
      )
      Text(
        text = "${grade.shortLabel} worksheets are ready to be added soon. Once uploaded, all activities and PDF Progress Reports will appear right here.",
        style = MaterialTheme.typography.bodyMedium,
        color = InkMuted,
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        AralGrade.entries.forEach { active ->
          OutlinedButton(
            onClick = { onSwitchToGrade(active) },
            modifier = Modifier
              .weight(1f)
              .defaultMinSize(minHeight = 48.dp),
            shape = RoundedCornerShape(14.dp),
          ) {
            Text("${active.gradeNumber}", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
