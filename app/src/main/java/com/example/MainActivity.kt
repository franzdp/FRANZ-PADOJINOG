package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.data.ProgressReportPdfGenerator
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OnboardingScreen
import com.example.ui.screens.WorksheetActivityScreen
import com.example.ui.theme.AralMathTheme
import com.example.viewmodel.AppLaunchRoute
import com.example.viewmodel.MathViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      AralMathTheme {
        AralMathApp()
      }
    }
  }
}

@Composable
fun AralMathApp(mathViewModel: MathViewModel = viewModel()) {
  val context = LocalContext.current
  val navController = rememberNavController()
  val homeState by mathViewModel.homeState.collectAsStateWithLifecycle()
  val activeState by mathViewModel.activeState.collectAsStateWithLifecycle()
  val navCommand by mathViewModel.navigationCommand.collectAsStateWithLifecycle()

  val createBackupLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.CreateDocument("application/json"),
  ) { uri ->
    if (uri != null) {
      mathViewModel.exportRecordsBackupToUri(uri)
    }
  }
  val openBackupLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.OpenDocument(),
  ) { uri ->
    if (uri != null) {
      mathViewModel.importRecordsBackupFromUri(uri)
    }
  }
  val launchSaveBackup = {
    createBackupLauncher.launch("ARAL-Math-Records-Backup.json")
  }
  val launchRestoreBackup = {
    openBackupLauncher.launch(arrayOf("application/json", "text/plain", "*/*"))
  }

  // Automatically open Messenger with the generated PDF Progress Report attached
  LaunchedEffect(homeState.pendingPdfReport) {
    val report = homeState.pendingPdfReport ?: return@LaunchedEffect
    val outcome = ProgressReportPdfGenerator.sharePdfViaMessenger(
      context = context,
      pdfFile = report.pdfFile,
      learnerName = report.learnerName,
      grade = report.grade,
    )
    mathViewModel.consumePendingPdfReport(outcome.message)
  }

  // Handle automatic redirects (e.g., matching returning learner -> resume where left off)
  LaunchedEffect(navCommand, homeState.isInitialized) {
    if (!homeState.isInitialized) return@LaunchedEffect
    when (val cmd = navCommand) {
      AppLaunchRoute.Loading -> Unit
      AppLaunchRoute.Onboarding -> {
        navController.navigate("onboarding") {
          popUpTo(0) { inclusive = true }
          launchSingleTop = true
        }
        mathViewModel.consumeNavigationCommand()
      }
      is AppLaunchRoute.ResumeWorksheet -> {
        navController.navigate("home") {
          popUpTo(0) { inclusive = true }
          launchSingleTop = true
        }
        navController.navigate("worksheet") {
          launchSingleTop = true
        }
        mathViewModel.consumeNavigationCommand()
      }
      AppLaunchRoute.NavigateHome -> {
        navController.navigate("home") {
          popUpTo(0) { inclusive = true }
          launchSingleTop = true
        }
        mathViewModel.consumeNavigationCommand()
      }
      AppLaunchRoute.Home -> Unit
    }
  }

  if (!homeState.isInitialized) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
    return
  }

  NavHost(
    navController = navController,
    startDestination = if (homeState.activeLearner != null) "home" else "onboarding",
  ) {
    composable("onboarding") {
      OnboardingScreen(
        state = homeState,
        onSubmitProfile = mathViewModel::submitLearnerProfile,
        onSendProgressReportForLearner = { learner ->
          mathViewModel.generateProgressReport(
            grade = learner.activeGrade,
            learnerOverride = learner,
          )
        },
        onSaveBackup = launchSaveBackup,
        onRestoreBackup = launchRestoreBackup,
        onDismissBanner = mathViewModel::clearStatusBanner,
      )
    }
    composable("home") {
      HomeScreen(
        state = homeState,
        onSelectGrade = mathViewModel::selectAralGrade,
        onSelectWorld = mathViewModel::selectWorld,
        onSearchChange = mathViewModel::updateSearch,
        onToggleHintsDefault = mathViewModel::setShowHintsDefault,
        onOpenWorksheet = { id, itemIndex ->
          if (mathViewModel.openWorksheet(id, itemIndex)) {
            navController.navigate("worksheet")
          }
        },
        onSwitchLearner = mathViewModel::switchLearner,
        onGenerateProgressReport = { mathViewModel.generateProgressReport() },
        onSaveBackup = launchSaveBackup,
        onRestoreBackup = launchRestoreBackup,
        onDismissBanner = mathViewModel::clearStatusBanner,
      )
    }
    composable("worksheet") {
      val current = activeState
      if (current != null) {
        val statusMap = remember(homeState.activeLearner, current.grade) {
          com.example.data.WorksheetCatalog.getWorksheets(current.grade).associate { ws ->
            ws.id to (homeState.activeLearner?.statusForWorksheet(ws.id, current.grade)
              ?: com.example.model.ActivityMasteryStatus.NOT_STARTED)
          }
        }
        WorksheetActivityScreen(
          state = current,
          learnerName = homeState.activeLearner?.profile?.name ?: "Learner",
          activityStatuses = statusMap,
          remainingUnpassedCount = homeState.activitiesNeedingAttention.size,
          onBack = {
            mathViewModel.closeWorksheet()
            navController.popBackStack()
          },
          onJumpToIndex = mathViewModel::jumpToIndex,
          onJumpToNextWrong = mathViewModel::jumpToNextWrongItem,
          onJumpToWorksheet = { id -> mathViewModel.openWorksheet(id, null) },
          onNext = mathViewModel::nextItem,
          onPrev = mathViewModel::previousItem,
          onToggleHint = mathViewModel::toggleHint,
          onToggleMiniLesson = mathViewModel::toggleMiniLesson,
          onToggleScratchpad = mathViewModel::toggleScratchpad,
          onRecordResult = mathViewModel::recordResult,
          onDismissCertificate = mathViewModel::dismissCertificate,
          onRetryWorksheet = mathViewModel::retryCurrentWorksheet,
          onProceedNextActivity = {
            mathViewModel.proceedToNextWorksheetFromCertificate()
          },
          onGenerateProgressReport = {
            mathViewModel.generateProgressReport(current.grade)
          },
        )
      }
    }
  }
}
