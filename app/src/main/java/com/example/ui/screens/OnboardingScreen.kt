package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SheetCoordinateHelper
import com.example.model.AralGrade
import com.example.model.LearnerProgressRecord
import com.example.ui.theme.*
import com.example.viewmodel.HomeUiState

@Composable
fun OnboardingScreen(
  state: HomeUiState,
  onSubmitProfile: (name: String, gradeLevel: String, school: String, chosenProgram: AralGrade) -> Unit,
  onSendProgressReportForLearner: (LearnerProgressRecord) -> Unit,
  onDismissBanner: () -> Unit,
) {
  var name by rememberSaveable { mutableStateOf("") }
  var gradeLevel by rememberSaveable { mutableStateOf("Grade 4") }
  var selectedProgram by rememberSaveable { mutableStateOf(state.currentGrade) }
  var school by rememberSaveable { mutableStateOf("Bagacay Elementary School") }
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(state.statusBannerMessage) {
    val msg = state.statusBannerMessage
    if (!msg.isNullOrBlank()) {
      snackbarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
      onDismissBanner()
    }
  }

  val matchedLearner = remember(name, state.knownLearners) {
    val norm = SheetCoordinateHelper.normalizeLearnerName(name)
    if (norm.isBlank()) null else state.knownLearners.firstOrNull { it.profile.normalizedKey == norm }
  }

  Scaffold(
    containerColor = MaterialTheme.colorScheme.background,
    contentWindowInsets = WindowInsets.safeDrawing,
    snackbarHost = { SnackbarHost(snackbarHostState) },
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(
              Brush.linearGradient(
                colors = listOf(BagacayBlue, BagacaySky, TealOcean),
              ),
            )
            .padding(20.dp),
        ) {
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
              Image(
                painter = painterResource(id = R.drawable.bagacay_school_logo),
                contentDescription = stringResource(R.string.school_logo_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                  .size(64.dp)
                  .clip(CircleShape)
                  .background(Color.White)
                  .border(2.dp, MangoGold, CircleShape),
              )
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = stringResource(R.string.hero_title),
                  style = MaterialTheme.typography.headlineSmall,
                  color = Color.White,
                )
                Text(
                  text = stringResource(R.string.onboarding_welcome),
                  style = MaterialTheme.typography.bodyLarge,
                  color = MangoGoldLight,
                  fontWeight = FontWeight.SemiBold,
                )
              }
            }
            Text(
              text = stringResource(R.string.onboarding_subtitle),
              style = MaterialTheme.typography.bodyMedium,
              color = Color.White.copy(alpha = 0.92f),
            )
            Surface(
              color = MangoGold,
              shape = RoundedCornerShape(12.dp),
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
              ) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = InkDark, modifier = Modifier.size(15.dp))
                Text(
                  text = stringResource(R.string.innovation_credit),
                  color = InkDark,
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = FontWeight.Bold,
                )
              }
            }
            Surface(
              color = Color.White.copy(alpha = 0.16f),
              shape = RoundedCornerShape(14.dp),
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
              ) {
                Icon(Icons.Default.Devices, contentDescription = null, tint = MangoGoldLight, modifier = Modifier.size(18.dp))
                Column {
                  Text(
                    text = "Detected Device Address: ${state.deviceAddress.formattedAddress}",
                    color = Color.White,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                  )
                  Text(
                    text = state.deviceAddress.sourceDescription,
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.labelSmall,
                  )
                }
              }
            }
          }
        }
      }

      // PDF Progress Report -> Messenger Card
      item {
        MessengerReportOnboardingCard(
          matchedLearner = matchedLearner,
          isGenerating = state.isGeneratingReport,
          onSendMatchedReport = {
            matchedLearner?.let(onSendProgressReportForLearner)
          },
        )
      }

      // Learner Check-in Card
      item {
        Card(
          shape = RoundedCornerShape(24.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth(),
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .border(1.5.dp, OutlineSoft, RoundedCornerShape(24.dp))
              .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            Text(
              text = "Learner Check-In",
              style = MaterialTheme.typography.titleLarge,
              color = InkDark,
            )

            OutlinedTextField(
              value = name,
              onValueChange = { name = it },
              label = { Text(stringResource(R.string.learner_name_label)) },
              placeholder = { Text("e.g., Juan Dela Cruz") },
              leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
              singleLine = true,
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("learner_name_input"),
            )

            if (matchedLearner != null) {
              val (resumeWs, resumeItem) = matchedLearner.resumeTarget(selectedProgram)
              Surface(
                color = LeafEmeraldLight,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                  .fillMaxWidth()
                  .border(1.dp, LeafEmerald, RoundedCornerShape(14.dp)),
              ) {
                Row(
                  modifier = Modifier.padding(12.dp),
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                  Icon(Icons.Default.Restore, contentDescription = null, tint = LeafEmerald)
                  Text(
                    text = if (selectedProgram.hasActivities) {
                      "Welcome back! ${matchedLearner.completedCountFor(selectedProgram)}/${selectedProgram.totalWorksheets} passed in ${selectedProgram.shortLabel}. You will resume at Worksheet $resumeWs (Item #${resumeItem + 1})."
                    } else {
                      "Welcome back! Choose ARAL 2, 3, 4, 5, or 6 to continue."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = InkDark,
                    fontWeight = FontWeight.SemiBold,
                  )
                }
              }
            }

            OutlinedTextField(
              value = gradeLevel,
              onValueChange = { gradeLevel = it },
              label = { Text(stringResource(R.string.learner_grade_label)) },
              leadingIcon = { Icon(Icons.Default.School, contentDescription = null) },
              singleLine = true,
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("learner_grade_input"),
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              items(listOf("Grade 2", "Grade 3", "Grade 4", "Grade 5", "Grade 6")) { g ->
                FilterChip(
                  selected = gradeLevel.equals(g, ignoreCase = true),
                  onClick = {
                    gradeLevel = g
                    selectedProgram = AralGrade.fromGradeLevelText(g)
                  },
                  label = { Text(g, fontWeight = FontWeight.SemiBold) },
                )
              }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              Text(
                text = "Which ARAL Math will you answer? (Grades 2–6)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = InkDark,
              )
              AralGrade.entries.chunked(3).forEach { rowGrades ->
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                  rowGrades.forEach { prog ->
                    val isSel = selectedProgram == prog
                    Surface(
                      color = if (isSel) BagacayBlue else WarmSurfaceVariant,
                      shape = RoundedCornerShape(16.dp),
                      modifier = Modifier
                        .weight(1f)
                        .border(
                          width = if (isSel) 2.dp else 1.dp,
                          color = if (isSel) BagacayBlue else OutlineSoft,
                          shape = RoundedCornerShape(16.dp),
                        )
                        .clickable {
                          selectedProgram = prog
                          gradeLevel = prog.shortLabel
                        }
                        .testTag("program_chip_${prog.gradeNumber}"),
                    ) {
                      Column(
                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                      ) {
                        Text(
                          text = "ARAL ${prog.gradeNumber}",
                          style = MaterialTheme.typography.labelLarge,
                          fontWeight = FontWeight.Bold,
                          color = if (isSel) Color.White else InkDark,
                        )
                        Text(
                          text = if (prog.hasActivities) "${prog.totalWorksheets} WS" else "Soon",
                          style = MaterialTheme.typography.labelSmall,
                          color = if (isSel) MangoGoldLight else InkMuted,
                        )
                      }
                    }
                  }
                }
              }
            }

            OutlinedTextField(
              value = school,
              onValueChange = { school = it },
              label = { Text(stringResource(R.string.learner_school_label)) },
              leadingIcon = { Icon(Icons.Default.LocationCity, contentDescription = null) },
              singleLine = true,
              shape = RoundedCornerShape(16.dp),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("learner_school_input"),
            )

            Button(
              onClick = { onSubmitProfile(name, gradeLevel, school, selectedProgram) },
              enabled = name.trim().isNotEmpty(),
              shape = RoundedCornerShape(18.dp),
              colors = ButtonDefaults.buttonColors(containerColor = BagacayBlue),
              modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .testTag("start_learning_button"),
            ) {
              Text(
                text = if (matchedLearner != null) "Resume ${selectedProgram.shortLabel}" else "Start ${selectedProgram.displayTitle}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
              )
              Spacer(Modifier.width(8.dp))
              Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
            }
          }
        }
      }

      if (state.knownLearners.isNotEmpty()) {
        item {
          Text(
            text = "Returning Learners (Tap your name to resume)",
            style = MaterialTheme.typography.titleMedium,
            color = InkDark,
            modifier = Modifier.padding(top = 4.dp),
          )
        }
        items(state.knownLearners, key = { it.profile.normalizedKey }) { learner ->
          ReturningLearnerCard(
            learner = learner,
            onClick = {
              onSubmitProfile(
                learner.profile.name,
                learner.profile.gradeLevel,
                learner.profile.school,
                learner.activeGrade,
              )
            },
          )
        }
      }

      item {
        Surface(
          color = WarmSurfaceVariant,
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, OutlineSoft, RoundedCornerShape(16.dp)),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
          ) {
            Image(
              painter = painterResource(id = R.drawable.bagacay_school_logo),
              contentDescription = stringResource(R.string.school_logo_desc),
              contentScale = ContentScale.Crop,
              modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color.White),
            )
            Column {
              Text(
                text = "Bagacay Elementary School • District of Culasi",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = InkDark,
              )
              Text(
                text = stringResource(R.string.innovation_credit),
                style = MaterialTheme.typography.labelSmall,
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
private fun MessengerReportOnboardingCard(
  matchedLearner: LearnerProgressRecord?,
  isGenerating: Boolean,
  onSendMatchedReport: () -> Unit,
) {
  Surface(
    color = BagacayBlueLight.copy(alpha = 0.55f),
    shape = RoundedCornerShape(20.dp),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.5.dp, BagacayBlue.copy(alpha = 0.65f), RoundedCornerShape(20.dp)),
  ) {
    Column(
      modifier = Modifier.padding(14.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        Icon(
          imageVector = Icons.Default.PictureAsPdf,
          contentDescription = null,
          tint = BagacayBlue,
        )
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "PDF Progress Report via Messenger",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = InkDark,
          )
          Text(
            text = "Tap Progress Report inside the app to create a PDF summary per activity (including repeated wrong items) and attach it straight to Messenger.",
            style = MaterialTheme.typography.bodySmall,
            color = InkMuted,
          )
        }
      }

      if (matchedLearner != null) {
        Button(
          onClick = onSendMatchedReport,
          enabled = !isGenerating,
          colors = ButtonDefaults.buttonColors(containerColor = BagacayBlue),
          modifier = Modifier
            .defaultMinSize(minHeight = 48.dp)
            .testTag("onboarding_progress_report_button"),
          shape = RoundedCornerShape(14.dp),
        ) {
          Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(Modifier.width(6.dp))
          Text(
            text = if (isGenerating) "Creating PDF..." else "Progress Report (${matchedLearner.profile.name})",
            style = MaterialTheme.typography.labelLarge,
          )
        }
      }
    }
  }
}

@Composable
private fun ReturningLearnerCard(
  learner: LearnerProgressRecord,
  onClick: () -> Unit,
) {
  val grade = learner.activeGrade
  val (resumeWs, resumeItem) = learner.resumeTarget(grade)
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick),
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, OutlineSoft, RoundedCornerShape(18.dp))
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      Box(
        modifier = Modifier
          .size(44.dp)
          .clip(CircleShape)
          .background(BagacayBlueLight),
        contentAlignment = Alignment.Center,
      ) {
        Text(
          text = learner.profile.name.take(1).uppercase(),
          fontWeight = FontWeight.Bold,
          fontSize = 20.sp,
          color = BagacayBlue,
        )
      }
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = learner.profile.name,
          style = MaterialTheme.typography.titleMedium,
          color = InkDark,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
        Text(
          text = "${learner.profile.gradeLevel} • ${learner.profile.school}",
          style = MaterialTheme.typography.bodySmall,
          color = InkMuted,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
        Text(
          text = if (grade.hasActivities) {
            "${grade.shortLabel}: ${learner.completedCountFor(grade)}/${grade.totalWorksheets} passed • Left off: WS $resumeWs #${resumeItem + 1}"
          } else {
            "${grade.shortLabel}: Activities coming soon"
          },
          style = MaterialTheme.typography.labelMedium,
          color = LeafEmerald,
        )
      }
      Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Resume ${learner.profile.name}", tint = BagacayBlue)
    }
  }
}
