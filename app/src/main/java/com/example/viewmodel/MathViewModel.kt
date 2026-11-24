package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.DeviceAddressInfo
import com.example.data.DeviceAddressProvider
import com.example.data.GoogleSheetsRepository
import com.example.data.LearnerRecordsStore
import com.example.data.PreferencesRepository
import com.example.data.ProgressReportPdfGenerator
import com.example.data.SheetCoordinateHelper
import com.example.data.SpreadsheetHandle
import com.example.data.UserSettings
import com.example.data.WorksheetCatalog
import com.example.data.WorksheetProblemsRepository
import com.example.model.ActivityMasteryStatus
import com.example.model.ActivityRecord
import com.example.model.AralGrade
import com.example.model.GradeProgressState
import com.example.model.LearnerProfile
import com.example.model.LearnerProgressRecord
import com.example.model.LearningWorld
import com.example.model.MathProblem
import com.example.model.WorksheetInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class PendingPdfReport(
  val pdfFile: File,
  val learnerName: String,
  val grade: AralGrade,
)

data class ActiveWorksheetUiState(
  val worksheet: WorksheetInfo,
  val allProblems: List<MathProblem>,
  val grade: AralGrade = worksheet.grade,
  val currentIndex: Int = 0,
  val solvedCorrectIndices: Set<Int> = emptySet(),
  val wrongAttemptIndices: Set<Int> = emptySet(),
  val attemptCounts: Map<Int, Int> = emptyMap(),
  val wrongAttemptHistory: Map<Int, Int> = emptyMap(),
  val feedback: AnswerCheckResult? = null,
  val streak: Int = 0,
  val showHint: Boolean = false,
  val showMiniLesson: Boolean = false,
  val showScratchpad: Boolean = false,
  val showCertificate: Boolean = false,
  val confettiTrigger: Int = 0,
) {
  val currentProblem: MathProblem? get() = allProblems.getOrNull(currentIndex)
  val currentPercent: Int
    get() = SheetCoordinateHelper.computePercent(solvedCorrectIndices.size, allProblems.size)
  val isPassed90: Boolean
    get() = SheetCoordinateHelper.hasPassedMastery(solvedCorrectIndices.size, allProblems.size)
  val isPerfect100: Boolean
    get() = allProblems.isNotEmpty() && solvedCorrectIndices.size >= allProblems.size
  val requiredCorrectFor90: Int
    get() = SheetCoordinateHelper.requiredCorrectForMastery(allProblems.size)
  val pendingWrongIndices: List<Int>
    get() = (wrongAttemptIndices - solvedCorrectIndices).sorted()
  val currentAttempts: Int
    get() = attemptCounts[currentIndex] ?: 0
  val isCurrentSolved: Boolean
    get() = currentIndex in solvedCorrectIndices
  val awaitingSecondTry: Boolean
    get() = !isCurrentSolved && currentAttempts == 1
  val canAdvanceFromCurrent: Boolean
    get() = !awaitingSecondTry
}

enum class SheetSyncStatus {
  DISCONNECTED,
  CONNECTING,
  SYNCING,
  CONNECTED,
  ERROR,
}

sealed interface AppLaunchRoute {
  data object Loading : AppLaunchRoute
  data object Onboarding : AppLaunchRoute
  data object Home : AppLaunchRoute
  data object NavigateHome : AppLaunchRoute
  data class ResumeWorksheet(val grade: AralGrade, val worksheetId: Int, val itemIndex: Int) : AppLaunchRoute
}

data class HomeUiState(
  val isInitialized: Boolean = false,
  val currentGrade: AralGrade = AralGrade.GRADE_4,
  val selectedWorld: LearningWorld? = null,
  val searchQuery: String = "",
  val settings: UserSettings = UserSettings(),
  val deviceAddress: DeviceAddressInfo = DeviceAddressInfo("00:00:00:00:00:00", "Detecting..."),
  val activeLearner: LearnerProgressRecord? = null,
  val knownLearners: List<LearnerProgressRecord> = emptyList(),
  val syncStatus: SheetSyncStatus = SheetSyncStatus.DISCONNECTED,
  val syncMessage: String? = null,
  val connectedEmail: String? = null,
  val spreadsheetUrl: String? = null,
  val statusBannerMessage: String? = null,
  val isCentralAutoSyncEnabled: Boolean = GoogleSheetsRepository.configuredWebAppUrl() != null,
  val isGeneratingReport: Boolean = false,
  val pendingPdfReport: PendingPdfReport? = null,
  val lastGeneratedPdfFile: File? = null,
) {
  val currentWorksheets: List<WorksheetInfo>
    get() = WorksheetCatalog.getWorksheets(currentGrade)

  val filteredWorksheets: List<WorksheetInfo>
    get() = currentWorksheets.filter { ws ->
      val worldOk = selectedWorld == null || ws.world == selectedWorld
      val q = searchQuery.trim()
      val queryOk = q.isEmpty() ||
        ws.title.contains(q, ignoreCase = true) ||
        "worksheet ${ws.id}".contains(q, ignoreCase = true) ||
        "ws ${ws.id}".contains(q, ignoreCase = true) ||
        ws.world.title.contains(q, ignoreCase = true)
      worldOk && queryOk
    }

  val scores: Map<Int, ActivityRecord>
    get() = activeLearner?.recordsFor(currentGrade) ?: emptyMap()

  val totalStars: Int
    get() = scores.values.sumOf { it.stars }

  val completedCount: Int
    get() = activeLearner?.completedCountFor(currentGrade) ?: 0

  val perfectCount: Int
    get() = activeLearner?.perfectCountFor(currentGrade) ?: 0

  val difficultyItemCount: Int
    get() = activeLearner?.difficultyItemCountFor(currentGrade) ?: 0

  val attemptedActivityCount: Int
    get() = scores.values.count { it.attemptedCount > 0 || it.completedRound }

  val totalActivityCount: Int
    get() = currentGrade.totalWorksheets

  val currentSheetRow: Int?
    get() = activeLearner?.progressFor(currentGrade)?.sheetRowNumber

  val passedOnlyCount: Int
    get() = currentWorksheets.count { statusFor(it.id) == ActivityMasteryStatus.PASSED }

  val notPassedCount: Int
    get() = currentWorksheets.count { statusFor(it.id) == ActivityMasteryStatus.NOT_PASSED }

  val notCompletedCount: Int
    get() = currentWorksheets.count { statusFor(it.id) == ActivityMasteryStatus.NOT_COMPLETED }

  val notStartedCount: Int
    get() = currentWorksheets.count { statusFor(it.id) == ActivityMasteryStatus.NOT_STARTED }

  val activitiesNeedingAttention: List<Int>
    get() = currentWorksheets.map { it.id }.filter { id ->
      val st = statusFor(id)
      st == ActivityMasteryStatus.NOT_STARTED ||
        st == ActivityMasteryStatus.NOT_COMPLETED ||
        st == ActivityMasteryStatus.NOT_PASSED
    }

  val completionFraction: Float
    get() = if (totalActivityCount > 0) (completedCount.toFloat() / totalActivityCount.toFloat()).coerceIn(0f, 1f) else 0f

  fun statusFor(worksheetId: Int): ActivityMasteryStatus {
    return activeLearner?.statusForWorksheet(worksheetId, currentGrade) ?: ActivityMasteryStatus.NOT_STARTED
  }
}

class MathViewModel(application: Application) : AndroidViewModel(application) {
  private val prefs = PreferencesRepository(application)
  private val recordsStore = LearnerRecordsStore(application)
  private val sheetsRepo = GoogleSheetsRepository()

  private val _homeState = MutableStateFlow(HomeUiState())
  val homeState: StateFlow<HomeUiState> = _homeState.asStateFlow()

  private val _activeState = MutableStateFlow<ActiveWorksheetUiState?>(null)
  val activeState: StateFlow<ActiveWorksheetUiState?> = _activeState.asStateFlow()

  private val _navigationCommand = MutableStateFlow<AppLaunchRoute>(AppLaunchRoute.Loading)
  val navigationCommand: StateFlow<AppLaunchRoute> = _navigationCommand.asStateFlow()

  private var accessToken: String? = null
  private var spreadsheetHandle: SpreadsheetHandle? = null
  private var debouncedSyncJob: Job? = null
  private var allLearnersMap: Map<String, LearnerProgressRecord> = emptyMap()
  private var activeLearnerKey: String? = null
  private val centralWebAppUrl: String? = GoogleSheetsRepository.configuredWebAppUrl()

  init {
    viewModelScope.launch {
      val fallbackUuid = recordsStore.getOrCreateFallbackUuid()
      val detectedDevice = DeviceAddressProvider.detectDeviceAddress(application, fallbackUuid)
      _homeState.update { it.copy(deviceAddress = detectedDevice) }

      val initialLearners = recordsStore.learnersFlow.first()
      val initialActiveKey = recordsStore.activeLearnerKeyFlow.first()
      val initialMeta = recordsStore.cloudMetaFlow.first()

      allLearnersMap = initialLearners
      activeLearnerKey = initialActiveKey

      val initialLearner = initialActiveKey?.let { initialLearners[it] }
      val initialGrade = initialLearner?.activeGrade ?: AralGrade.GRADE_4
      _homeState.update {
        it.copy(
          isInitialized = true,
          currentGrade = initialGrade,
          activeLearner = initialLearner,
          knownLearners = initialLearners.values.sortedBy { r -> r.profile.name.lowercase() },
          connectedEmail = initialMeta.connectedEmail ?: if (centralWebAppUrl != null) SheetCoordinateHelper.TEACHER_EMAIL else null,
          spreadsheetUrl = initialMeta.spreadsheetUrl,
          isCentralAutoSyncEnabled = centralWebAppUrl != null,
        )
      }

      if (initialLearner != null) {
        val (resumeWs, resumeItem) = initialLearner.resumeTarget(initialGrade)
        if (openWorksheet(resumeWs, resumeItem, initialGrade)) {
          _navigationCommand.value = AppLaunchRoute.ResumeWorksheet(initialGrade, resumeWs, resumeItem)
        } else {
          _navigationCommand.value = AppLaunchRoute.Home
        }
      } else {
        _navigationCommand.value = AppLaunchRoute.Onboarding
      }

      if (centralWebAppUrl != null) {
        launch {
          syncAllCentralSheetInternal()
        }
      }

      launch {
        prefs.settingsFlow.collect { settings ->
          _homeState.update { it.copy(settings = settings) }
        }
      }

      launch {
        recordsStore.learnersFlow.collect { map ->
          allLearnersMap = map
          val active = activeLearnerKey?.let { map[it] }
          _homeState.update { state ->
            state.copy(
              activeLearner = active,
              currentGrade = active?.activeGrade ?: state.currentGrade,
              knownLearners = map.values.sortedBy { r -> r.profile.name.lowercase() },
            )
          }
        }
      }

      launch {
        recordsStore.cloudMetaFlow.collect { meta ->
          _homeState.update {
            it.copy(
              connectedEmail = meta.connectedEmail ?: it.connectedEmail,
              spreadsheetUrl = meta.spreadsheetUrl ?: it.spreadsheetUrl,
            )
          }
        }
      }
    }
  }

  fun consumeNavigationCommand() {
    _navigationCommand.value = AppLaunchRoute.Home
  }

  fun clearStatusBanner() {
    _homeState.update { it.copy(statusBannerMessage = null) }
  }

  fun generateProgressReport(
    grade: AralGrade = _homeState.value.currentGrade,
    learnerOverride: LearnerProgressRecord? = null,
  ) {
    val targetLearner = learnerOverride ?: _homeState.value.activeLearner ?: run {
      _homeState.update { it.copy(statusBannerMessage = "Please check in a learner first to generate a Progress Report.") }
      return
    }
    if (_homeState.value.isGeneratingReport) return
    _homeState.update { it.copy(isGeneratingReport = true) }

    viewModelScope.launch {
      runCatching {
        withContext(Dispatchers.IO) {
          ProgressReportPdfGenerator.generatePdf(getApplication(), targetLearner, grade)
        }
      }.onSuccess { pdfFile ->
        _homeState.update {
          it.copy(
            isGeneratingReport = false,
            lastGeneratedPdfFile = pdfFile,
            pendingPdfReport = PendingPdfReport(
              pdfFile = pdfFile,
              learnerName = targetLearner.profile.name,
              grade = grade,
            ),
          )
        }
      }.onFailure { err ->
        _homeState.update {
          it.copy(
            isGeneratingReport = false,
            statusBannerMessage = "Could not generate PDF report: ${err.localizedMessage ?: "Unknown error"}",
          )
        }
      }
    }
  }

  fun consumePendingPdfReport(bannerMessage: String? = null) {
    _homeState.update {
      it.copy(
        pendingPdfReport = null,
        statusBannerMessage = bannerMessage ?: it.statusBannerMessage,
      )
    }
  }

  fun selectAralGrade(grade: AralGrade) {
    val learner = _homeState.value.activeLearner
    _homeState.update {
      it.copy(
        currentGrade = grade,
        selectedWorld = null,
        searchQuery = "",
      )
    }
    if (learner != null && learner.activeGrade != grade) {
      val switched = learner.switchActiveGrade(grade)
      _homeState.update { it.copy(activeLearner = switched) }
      viewModelScope.launch {
        recordsStore.upsertLearner(switched)
      }
    }
  }

  fun selectWorld(world: LearningWorld?) {
    _homeState.update { it.copy(selectedWorld = if (it.selectedWorld == world) null else world) }
  }

  fun updateSearch(query: String) {
    _homeState.update { it.copy(searchQuery = query) }
  }

  fun setShowHintsDefault(enabled: Boolean) {
    viewModelScope.launch { prefs.setShowHintsByDefault(enabled) }
  }

  fun onGoogleAuthorized(token: String) {
    accessToken = token
    viewModelScope.launch {
      _homeState.update {
        it.copy(
          syncStatus = SheetSyncStatus.CONNECTING,
          syncMessage = "Connecting to Google Sheets...",
        )
      }
      runCatching {
        val email = sheetsRepo.fetchUserEmail(token)
        val savedMeta = recordsStore.cloudMetaFlow.first()
        val handle = sheetsRepo.findOrCreateSpreadsheet(
          accessToken = token,
          savedSpreadsheetId = savedMeta.spreadsheetId,
          connectedEmail = email,
        )
        spreadsheetHandle = handle
        recordsStore.saveCloudMeta(
          spreadsheetId = handle.spreadsheetId,
          spreadsheetUrl = handle.spreadsheetUrl,
          connectedEmail = email ?: savedMeta.connectedEmail,
        )

        for (grade in AralGrade.entries) {
          val cloudLearners = sheetsRepo.readAllLearners(token, handle, grade)
          recordsStore.mergeFromCloud(cloudLearners, grade)
        }

        val mergedMap = recordsStore.learnersFlow.first()
        for (learner in mergedMap.values) {
          var currentLearner = learner
          for (grade in AralGrade.entries) {
            val gp = currentLearner.progressFor(grade)
            val hasActivityOrActive = gp.records.isNotEmpty() || currentLearner.activeGrade == grade
            if (hasActivityOrActive && (gp.pendingSheetSync || gp.sheetRowNumber == null)) {
              val assignedRow = sheetsRepo.upsertLearnerRow(token, handle, currentLearner, grade)
              val syncedRecords = gp.records.mapValues { (_, rec) -> rec.copy(syncedToSheet = true) }
              val cleanedGp = gp.copy(
                records = syncedRecords,
                pendingSheetSync = false,
                sheetRowNumber = assignedRow,
              )
              currentLearner = currentLearner.copy(
                profile = if (grade == AralGrade.GRADE_4) {
                  currentLearner.profile.copy(sheetRowNumber = assignedRow)
                } else {
                  currentLearner.profile
                },
              ).withGradeProgress(grade, cleanedGp)
            }
          }
          if (currentLearner != learner) {
            recordsStore.upsertLearner(currentLearner)
          }
        }
        email
      }.onSuccess { email ->
        val displayEmail = email ?: SheetCoordinateHelper.TEACHER_EMAIL
        _homeState.update {
          it.copy(
            syncStatus = SheetSyncStatus.CONNECTED,
            syncMessage = "Synced with Google Sheets ($displayEmail)",
          )
        }
      }.onFailure { err ->
        _homeState.update {
          it.copy(
            syncStatus = SheetSyncStatus.ERROR,
            syncMessage = err.localizedMessage ?: "Google Sheets sync failed.",
          )
        }
      }
    }
  }

  fun onGoogleAuthError(message: String) {
    _homeState.update {
      it.copy(
        syncStatus = SheetSyncStatus.ERROR,
        syncMessage = message,
      )
    }
  }

  fun syncAllNow() {
    if (centralWebAppUrl != null) {
      viewModelScope.launch {
        syncAllCentralSheetInternal()
      }
      return
    }
    val token = accessToken
    if (token.isNullOrBlank()) {
      _homeState.update {
        it.copy(
          syncStatus = SheetSyncStatus.ERROR,
          syncMessage = "Please connect Google Sheets first.",
        )
      }
      return
    }
    onGoogleAuthorized(token)
  }

  private suspend fun syncAllCentralSheetInternal() {
    val webAppUrl = centralWebAppUrl ?: return
    _homeState.update {
      it.copy(
        syncStatus = SheetSyncStatus.CONNECTING,
        syncMessage = "Connecting to Teacher Google Sheet (${SheetCoordinateHelper.TEACHER_EMAIL})...",
      )
    }
    runCatching {
      val syncRes = sheetsRepo.readAllGradesFromWebApp(webAppUrl)
      var ssUrl = syncRes.spreadsheetUrl ?: recordsStore.cloudMetaFlow.first().spreadsheetUrl
      for (grade in AralGrade.entries) {
        val list = syncRes.byGrade[grade].orEmpty()
        recordsStore.mergeFromCloud(list, grade)
      }

      val mergedMap = recordsStore.learnersFlow.first()
      for (learner in mergedMap.values) {
        var currentLearner = learner
        for (grade in AralGrade.entries) {
          val gp = currentLearner.progressFor(grade)
          val hasActivityOrActive = gp.records.isNotEmpty() || currentLearner.activeGrade == grade
          if (hasActivityOrActive && (gp.pendingSheetSync || gp.sheetRowNumber == null)) {
            val upsertRes = sheetsRepo.upsertLearnerViaWebApp(webAppUrl, currentLearner, grade)
            if (!upsertRes.spreadsheetUrl.isNullOrBlank()) ssUrl = upsertRes.spreadsheetUrl
            val syncedRecords = gp.records.mapValues { (_, rec) -> rec.copy(syncedToSheet = true) }
            val cleanedGp = gp.copy(
              records = syncedRecords,
              pendingSheetSync = false,
              sheetRowNumber = upsertRes.rowNumber,
            )
            currentLearner = currentLearner.copy(
              profile = if (grade == AralGrade.GRADE_4) {
                currentLearner.profile.copy(sheetRowNumber = upsertRes.rowNumber)
              } else {
                currentLearner.profile
              },
            ).withGradeProgress(grade, cleanedGp)
          }
        }
        if (currentLearner != learner) {
          recordsStore.upsertLearner(currentLearner)
        }
      }

      recordsStore.saveCloudMeta(
        spreadsheetId = "central_webapp",
        spreadsheetUrl = ssUrl,
        connectedEmail = SheetCoordinateHelper.TEACHER_EMAIL,
      )
      ssUrl
    }.onSuccess {
      _homeState.update {
        it.copy(
          syncStatus = SheetSyncStatus.CONNECTED,
          syncMessage = "Auto-connected to Teacher Sheet (${SheetCoordinateHelper.TEACHER_EMAIL})",
        )
      }
    }.onFailure { err ->
      _homeState.update {
        it.copy(
          syncStatus = SheetSyncStatus.ERROR,
          syncMessage = err.localizedMessage ?: "Central Google Sheet sync failed.",
        )
      }
    }
  }

  fun submitLearnerProfile(
    rawName: String,
    rawGradeLevel: String,
    rawSchool: String,
    chosenProgram: AralGrade = AralGrade.fromGradeLevelText(rawGradeLevel),
  ) {
    val cleanName = rawName.trim().replace(Regex("\\s+"), " ")
    if (cleanName.isBlank()) return
    val cleanGrade = rawGradeLevel.trim().ifBlank { chosenProgram.shortLabel }
    val cleanSchool = rawSchool.trim().ifBlank { "Bagacay Elementary School" }
    val deviceAddr = _homeState.value.deviceAddress.formattedAddress
    val normalizedKey = SheetCoordinateHelper.normalizeLearnerName(cleanName)

    viewModelScope.launch {
      val webAppUrl = centralWebAppUrl
      if (webAppUrl != null) {
        runCatching {
          val (ssUrl, cloudLearners) = sheetsRepo.readGradeFromWebApp(webAppUrl, chosenProgram)
          recordsStore.mergeFromCloud(cloudLearners, chosenProgram)
          if (!ssUrl.isNullOrBlank()) {
            recordsStore.saveCloudMeta("central_webapp", ssUrl, SheetCoordinateHelper.TEACHER_EMAIL)
          }
        }
      } else {
        val token = accessToken
        val handle = spreadsheetHandle
        if (!token.isNullOrBlank() && handle != null) {
          runCatching {
            val cloudLearners = sheetsRepo.readAllLearners(token, handle, chosenProgram)
            recordsStore.mergeFromCloud(cloudLearners, chosenProgram)
          }
        }
      }

      val latestMap = recordsStore.learnersFlow.first()
      val existing = latestMap[normalizedKey]
      activeLearnerKey = normalizedKey
      recordsStore.setActiveLearnerKey(normalizedKey)

      if (existing != null) {
        val switched = existing.switchActiveGrade(chosenProgram)
        val gp = switched.progressFor(chosenProgram)
        val updated = switched.copy(
          profile = switched.profile.copy(
            name = cleanName,
            gradeLevel = cleanGrade,
            school = cleanSchool,
            deviceAddress = deviceAddr,
          ),
        ).withGradeProgress(chosenProgram, gp.copy(pendingSheetSync = true))
        recordsStore.upsertLearner(updated)

        val (resumeWs, resumeItem) = updated.resumeTarget(chosenProgram)
        val hasWrong = (updated.recordsFor(chosenProgram)[resumeWs]?.pendingWrongIndices?.isNotEmpty() == true)
        _homeState.update {
          it.copy(
            currentGrade = chosenProgram,
            selectedWorld = null,
            activeLearner = updated,
            statusBannerMessage = when {
              !chosenProgram.hasActivities ->
                "Welcome back, $cleanName! (${chosenProgram.shortLabel}) Activities coming soon."
              hasWrong ->
                "Welcome back, $cleanName! (${chosenProgram.shortLabel}) Redirected to wrong items in Worksheet $resumeWs to aim for 100% ★."
              else ->
                "Welcome back, $cleanName! (${chosenProgram.shortLabel}) Continuing Worksheet $resumeWs, Item #${resumeItem + 1}."
            },
          )
        }
        syncLearnerToSheetAsync(updated, chosenProgram)
        if (openWorksheet(resumeWs, resumeItem, chosenProgram)) {
          _navigationCommand.value = AppLaunchRoute.ResumeWorksheet(chosenProgram, resumeWs, resumeItem)
        } else {
          _navigationCommand.value = AppLaunchRoute.NavigateHome
        }
      } else {
        val hasRemoteSync = centralWebAppUrl != null || spreadsheetHandle != null
        val nextRow = ((latestMap.values.mapNotNull { it.progressFor(chosenProgram).sheetRowNumber }.maxOrNull() ?: 3) + 1)
          .coerceAtLeast(SheetCoordinateHelper.FIRST_LEARNER_ROW)
        val initialGp = GradeProgressState(
          lastWorksheetId = 1,
          lastItemIndex = 0,
          pendingSheetSync = true,
          sheetRowNumber = if (hasRemoteSync) null else nextRow,
        )
        val created = LearnerProgressRecord(
          profile = LearnerProfile(
            name = cleanName,
            gradeLevel = cleanGrade,
            school = cleanSchool,
            deviceAddress = deviceAddr,
            sheetRowNumber = if (chosenProgram == AralGrade.GRADE_4 && !hasRemoteSync) nextRow else null,
          ),
          lastWorksheetId = 1,
          lastItemIndex = 0,
          pendingSheetSync = true,
          activeGrade = chosenProgram,
          gradeProgress = mapOf(chosenProgram to initialGp),
        )
        recordsStore.upsertLearner(created)
        _homeState.update {
          it.copy(
            currentGrade = chosenProgram,
            selectedWorld = null,
            activeLearner = created,
            statusBannerMessage = if (chosenProgram.hasActivities) {
              "Welcome, $cleanName! (${chosenProgram.shortLabel}) Aim for 100% Perfect Score ★!"
            } else {
              "Welcome, $cleanName! (${chosenProgram.shortLabel}) Profile saved! Activities coming soon."
            },
          )
        }
        syncLearnerToSheetAsync(created, chosenProgram)
        if (openWorksheet(1, 0, chosenProgram)) {
          _navigationCommand.value = AppLaunchRoute.ResumeWorksheet(chosenProgram, 1, 0)
        } else {
          _navigationCommand.value = AppLaunchRoute.NavigateHome
        }
      }
    }
  }

  fun switchLearner() {
    val cur = _activeState.value
    if (cur != null) {
      saveResumePointer(
        grade = cur.grade,
        worksheetId = cur.worksheet.id,
        itemIndex = cur.currentIndex,
        correctIndices = cur.solvedCorrectIndices,
        wrongIndices = cur.wrongAttemptIndices,
        wrongAttemptHistory = cur.wrongAttemptHistory,
        completedRound = cur.showCertificate,
      )
    }
    _activeState.value = null
    activeLearnerKey = null
    viewModelScope.launch {
      recordsStore.setActiveLearnerKey(null)
    }
    _homeState.update { it.copy(activeLearner = null) }
    _navigationCommand.value = AppLaunchRoute.Onboarding
  }

  /**
   * Opens any activity freely within the active ARAL grade.
   */
  fun openWorksheet(
    worksheetId: Int,
    startItemIndex: Int? = null,
    grade: AralGrade = _homeState.value.currentGrade,
  ): Boolean {
    val catalog = WorksheetCatalog.getWorksheets(grade)
    val info = catalog.firstOrNull { it.id == worksheetId } ?: return false
    val problems = WorksheetProblemsRepository.getProblemsForWorksheet(grade, worksheetId)
    if (problems.isEmpty()) return false

    val learner = _homeState.value.activeLearner
    val gp = learner?.progressFor(grade)
    val showHints = _homeState.value.settings.showHintsByDefault

    val savedRec = gp?.records?.get(worksheetId)
    val restoredCorrect = when {
      savedRec != null && (savedRec.correctIndices.isNotEmpty() || savedRec.wrongIndices.isNotEmpty()) ->
        savedRec.correctIndices
      gp?.inProgressWorksheetId == worksheetId ->
        gp.inProgressCorrectIndices
      else -> emptySet()
    }
    val restoredWrong = when {
      savedRec != null && (savedRec.correctIndices.isNotEmpty() || savedRec.wrongIndices.isNotEmpty()) ->
        savedRec.wrongIndices
      gp?.inProgressWorksheetId == worksheetId ->
        gp.inProgressWrongIndices
      else -> emptySet()
    }
    val restoredWrongHistory = when {
      savedRec != null && savedRec.wrongAttemptCounts.isNotEmpty() ->
        savedRec.wrongAttemptCounts
      gp?.inProgressWorksheetId == worksheetId ->
        gp.inProgressWrongAttemptCounts
      else -> emptyMap()
    }

    val chosenIndex = startItemIndex ?: learner?.recommendedStartIndexFor(worksheetId, grade) ?: 0
    val safeIndex = chosenIndex.coerceIn(0, (problems.size - 1).coerceAtLeast(0))

    _activeState.value = ActiveWorksheetUiState(
      worksheet = info,
      allProblems = problems,
      grade = grade,
      currentIndex = safeIndex,
      solvedCorrectIndices = restoredCorrect,
      wrongAttemptIndices = restoredWrong,
      wrongAttemptHistory = restoredWrongHistory,
      showHint = showHints,
    )

    saveResumePointer(
      grade = grade,
      worksheetId = worksheetId,
      itemIndex = safeIndex,
      correctIndices = restoredCorrect,
      wrongIndices = restoredWrong,
      wrongAttemptHistory = restoredWrongHistory,
      completedRound = savedRec?.completedRound ?: false,
    )
    return true
  }

  fun closeWorksheet() {
    val cur = _activeState.value
    if (cur != null) {
      val prevCompleted = _homeState.value.activeLearner?.recordsFor(cur.grade)?.get(cur.worksheet.id)?.completedRound ?: false
      saveResumePointer(
        grade = cur.grade,
        worksheetId = cur.worksheet.id,
        itemIndex = cur.currentIndex,
        correctIndices = cur.solvedCorrectIndices,
        wrongIndices = cur.wrongAttemptIndices,
        wrongAttemptHistory = cur.wrongAttemptHistory,
        completedRound = prevCompleted || cur.showCertificate,
      )
      _homeState.value.activeLearner?.let { syncLearnerToSheetAsync(it, cur.grade) }
    }
    _activeState.value = null
  }

  fun jumpToIndex(index: Int) {
    val cur = _activeState.value ?: return
    if (index in cur.allProblems.indices) {
      val prevCompleted = _homeState.value.activeLearner?.recordsFor(cur.grade)?.get(cur.worksheet.id)?.completedRound ?: false
      _activeState.value = cur.copy(
        currentIndex = index,
        attemptCounts = cur.attemptCounts - cur.currentIndex - index,
        feedback = null,
        showHint = _homeState.value.settings.showHintsByDefault,
      )
      saveResumePointer(cur.grade, cur.worksheet.id, index, cur.solvedCorrectIndices, cur.wrongAttemptIndices, cur.wrongAttemptHistory, prevCompleted)
    }
  }

  fun jumpToNextWrongItem() {
    val cur = _activeState.value ?: return
    val wrong = cur.pendingWrongIndices
    if (wrong.isEmpty()) return
    val nextWrong = wrong.firstOrNull { it > cur.currentIndex } ?: wrong.first()
    jumpToIndex(nextWrong)
  }

  fun nextItem() {
    val cur = _activeState.value ?: return
    if (cur.awaitingSecondTry) return
    val prevCompleted = _homeState.value.activeLearner?.recordsFor(cur.grade)?.get(cur.worksheet.id)?.completedRound ?: false
    if (cur.currentIndex < cur.allProblems.lastIndex) {
      val nextIdx = cur.currentIndex + 1
      _activeState.value = cur.copy(
        currentIndex = nextIdx,
        attemptCounts = cur.attemptCounts - cur.currentIndex - nextIdx,
        feedback = null,
        showHint = _homeState.value.settings.showHintsByDefault,
      )
      saveResumePointer(cur.grade, cur.worksheet.id, nextIdx, cur.solvedCorrectIndices, cur.wrongAttemptIndices, cur.wrongAttemptHistory, prevCompleted)
    } else {
      _activeState.value = cur.copy(
        attemptCounts = cur.attemptCounts - cur.currentIndex,
        showCertificate = true,
      )
      saveResumePointer(cur.grade, cur.worksheet.id, cur.currentIndex, cur.solvedCorrectIndices, cur.wrongAttemptIndices, cur.wrongAttemptHistory, completedRound = true)
      _homeState.value.activeLearner?.let { syncLearnerToSheetAsync(it, cur.grade) }
    }
  }

  fun previousItem() {
    val cur = _activeState.value ?: return
    if (cur.currentIndex > 0) {
      val prevIdx = cur.currentIndex - 1
      val prevCompleted = _homeState.value.activeLearner?.recordsFor(cur.grade)?.get(cur.worksheet.id)?.completedRound ?: false
      _activeState.value = cur.copy(
        currentIndex = prevIdx,
        attemptCounts = cur.attemptCounts - cur.currentIndex - prevIdx,
        feedback = null,
        showHint = _homeState.value.settings.showHintsByDefault,
      )
      saveResumePointer(cur.grade, cur.worksheet.id, prevIdx, cur.solvedCorrectIndices, cur.wrongAttemptIndices, cur.wrongAttemptHistory, prevCompleted)
    }
  }

  fun toggleHint() {
    _activeState.update { it?.copy(showHint = !it.showHint) }
  }

  fun toggleMiniLesson(show: Boolean? = null) {
    _activeState.update { state -> state?.copy(showMiniLesson = show ?: !state.showMiniLesson) }
  }

  fun toggleScratchpad(show: Boolean? = null) {
    _activeState.update { state -> state?.copy(showScratchpad = show ?: !state.showScratchpad) }
  }

  fun dismissCertificate() {
    _activeState.update { it?.copy(showCertificate = false) }
  }

  fun retryCurrentWorksheet() {
    val cur = _activeState.value ?: return
    val firstWrong = cur.pendingWrongIndices.firstOrNull() ?: 0
    _activeState.value = cur.copy(
      currentIndex = firstWrong,
      attemptCounts = emptyMap(),
      feedback = null,
      showHint = _homeState.value.settings.showHintsByDefault,
      showCertificate = false,
    )
    saveResumePointer(cur.grade, cur.worksheet.id, firstWrong, cur.solvedCorrectIndices, cur.wrongAttemptIndices, cur.wrongAttemptHistory, completedRound = false)
  }

  fun proceedToNextWorksheetFromCertificate(): Boolean {
    val cur = _activeState.value ?: return false
    val nextId = cur.worksheet.id + 1
    if (nextId > cur.grade.totalWorksheets) return false
    return openWorksheet(nextId, null, cur.grade)
  }

  fun recordResult(result: AnswerCheckResult) {
    val cur = _activeState.value ?: return
    val grade = cur.grade
    val idx = cur.currentIndex
    val newCorrect = if (result.isCorrect) cur.solvedCorrectIndices + idx else cur.solvedCorrectIndices - idx
    val newWrong = if (!result.isCorrect) cur.wrongAttemptIndices + idx else cur.wrongAttemptIndices - idx
    val newAttemptCounts = if (result.isCorrect) {
      cur.attemptCounts - idx
    } else {
      cur.attemptCounts + (idx to ((cur.attemptCounts[idx] ?: 0) + 1))
    }
    val newWrongHistory = if (!result.isCorrect) {
      cur.wrongAttemptHistory + (idx to ((cur.wrongAttemptHistory[idx] ?: 0) + 1))
    } else {
      cur.wrongAttemptHistory
    }
    val newShowHint = if (!result.isCorrect) true else cur.showHint
    val newStreak = if (result.isCorrect) cur.streak + 1 else 0
    val newConfetti = if (result.isCorrect) cur.confettiTrigger + 1 else cur.confettiTrigger

    _activeState.value = cur.copy(
      solvedCorrectIndices = newCorrect,
      wrongAttemptIndices = newWrong,
      attemptCounts = newAttemptCounts,
      wrongAttemptHistory = newWrongHistory,
      feedback = result,
      showHint = newShowHint,
      streak = newStreak,
      confettiTrigger = newConfetti,
    )

    val wsId = cur.worksheet.id
    val totalItems = cur.allProblems.size
    val correctCount = newCorrect.size
    val pct = SheetCoordinateHelper.computePercent(correctCount, totalItems)
    val stars = when {
      pct >= 100 -> 3
      pct >= 90 -> 3
      pct >= 50 -> 2
      correctCount > 0 -> 1
      else -> 0
    }
    val timestamp = currentTimestampString()

    val learner = _homeState.value.activeLearner ?: return
    val gp = learner.progressFor(grade)
    val prevRec = gp.records[wsId]
    val prevWasCloudOnly = prevRec != null && prevRec.correctIndices.isEmpty() && prevRec.wrongIndices.isEmpty()
    val allAttemptedNow = (newCorrect + newWrong).size >= totalItems
    val completedRoundNow = (prevRec?.completedRound == true) || allAttemptedNow || cur.currentIndex == cur.allProblems.lastIndex

    val updatedRecord = if (prevWasCloudOnly && prevRec != null && prevRec.correctCount > correctCount) {
      prevRec.copy(
        correctIndices = newCorrect,
        wrongIndices = newWrong,
        wrongAttemptCounts = newWrongHistory,
        lastItemIndex = idx,
        completedRound = prevRec.completedRound,
      )
    } else {
      ActivityRecord(
        worksheetId = wsId,
        correctCount = correctCount,
        totalItems = totalItems,
        stars = maxOf(prevRec?.stars ?: 0, stars),
        timestamp = timestamp,
        syncedToSheet = false,
        correctIndices = newCorrect,
        wrongIndices = newWrong,
        wrongAttemptCounts = newWrongHistory,
        lastItemIndex = idx,
        completedRound = completedRoundNow,
      )
    }

    val updatedGp = gp.copy(
      records = gp.records + (wsId to updatedRecord),
      lastWorksheetId = wsId,
      lastItemIndex = idx,
      inProgressWorksheetId = wsId,
      inProgressCorrectIndices = newCorrect,
      inProgressWrongIndices = newWrong,
      inProgressWrongAttemptCounts = newWrongHistory,
      pendingSheetSync = true,
    )
    val updatedLearner = learner.withGradeProgress(grade, updatedGp)

    _homeState.update { it.copy(activeLearner = updatedLearner) }
    viewModelScope.launch {
      recordsStore.upsertLearner(updatedLearner)
    }
    scheduleDebouncedSheetSync(updatedLearner, grade)
  }

  private fun saveResumePointer(
    grade: AralGrade,
    worksheetId: Int,
    itemIndex: Int,
    correctIndices: Set<Int>,
    wrongIndices: Set<Int>,
    wrongAttemptHistory: Map<Int, Int>,
    completedRound: Boolean,
  ) {
    val learner = _homeState.value.activeLearner ?: return
    val gp = learner.progressFor(grade)
    val totalItems = WorksheetProblemsRepository.getProblemsForWorksheet(grade, worksheetId).size
    val existingRec = gp.records[worksheetId]
    val mergedWrongHistory = if (wrongAttemptHistory.isNotEmpty()) wrongAttemptHistory else existingRec?.wrongAttemptCounts.orEmpty()
    val updatedRec = if (existingRec != null) {
      existingRec.copy(
        correctIndices = if (correctIndices.isNotEmpty() || existingRec.correctIndices.isEmpty()) correctIndices else existingRec.correctIndices,
        wrongIndices = if (wrongIndices.isNotEmpty() || existingRec.wrongIndices.isEmpty()) wrongIndices else existingRec.wrongIndices,
        wrongAttemptCounts = mergedWrongHistory,
        lastItemIndex = itemIndex,
        completedRound = existingRec.completedRound || completedRound,
      )
    } else if (correctIndices.isNotEmpty() || wrongIndices.isNotEmpty() || completedRound) {
      ActivityRecord(
        worksheetId = worksheetId,
        correctCount = correctIndices.size,
        totalItems = totalItems,
        stars = if (correctIndices.isNotEmpty()) 1 else 0,
        timestamp = currentTimestampString(),
        syncedToSheet = false,
        correctIndices = correctIndices,
        wrongIndices = wrongIndices,
        wrongAttemptCounts = mergedWrongHistory,
        lastItemIndex = itemIndex,
        completedRound = completedRound,
      )
    } else null

    val newRecords = if (updatedRec != null) gp.records + (worksheetId to updatedRec) else gp.records
    val updatedGp = gp.copy(
      records = newRecords,
      lastWorksheetId = worksheetId,
      lastItemIndex = itemIndex,
      inProgressWorksheetId = worksheetId,
      inProgressCorrectIndices = correctIndices,
      inProgressWrongIndices = wrongIndices,
      inProgressWrongAttemptCounts = mergedWrongHistory,
    )
    val updated = learner.withGradeProgress(grade, updatedGp)
    _homeState.update { it.copy(activeLearner = updated) }
    viewModelScope.launch {
      recordsStore.upsertLearner(updated)
    }
  }

  private fun scheduleDebouncedSheetSync(record: LearnerProgressRecord, grade: AralGrade) {
    debouncedSyncJob?.cancel()
    debouncedSyncJob = viewModelScope.launch {
      delay(1500)
      syncLearnerToSheetInternal(record, grade)
    }
  }

  private fun syncLearnerToSheetAsync(record: LearnerProgressRecord, grade: AralGrade) {
    debouncedSyncJob?.cancel()
    viewModelScope.launch {
      syncLearnerToSheetInternal(record, grade)
    }
  }

  private suspend fun syncLearnerToSheetInternal(record: LearnerProgressRecord, grade: AralGrade) {
    val webAppUrl = centralWebAppUrl
    if (webAppUrl != null) {
      _homeState.update { it.copy(syncStatus = SheetSyncStatus.SYNCING, syncMessage = "Auto-saving ${grade.shortLabel} to Teacher Sheet...") }
      runCatching {
        val res = sheetsRepo.upsertLearnerViaWebApp(webAppUrl, record, grade)
        val assignedRow = res.rowNumber
        val gp = record.progressFor(grade)
        val syncedRecords = gp.records.mapValues { (_, rec) -> rec.copy(syncedToSheet = true) }
        val cleanedGp = gp.copy(
          records = syncedRecords,
          pendingSheetSync = false,
          sheetRowNumber = assignedRow,
        )
        val cleaned = record.copy(
          profile = if (grade == AralGrade.GRADE_4) record.profile.copy(sheetRowNumber = assignedRow) else record.profile,
        ).withGradeProgress(grade, cleanedGp)
        recordsStore.upsertLearner(cleaned)
        if (!res.spreadsheetUrl.isNullOrBlank()) {
          recordsStore.saveCloudMeta("central_webapp", res.spreadsheetUrl, SheetCoordinateHelper.TEACHER_EMAIL)
        }
        cleaned to assignedRow
      }.onSuccess { (cleaned, rowNum) ->
        if (_homeState.value.activeLearner?.profile?.normalizedKey == cleaned.profile.normalizedKey) {
          _homeState.update { it.copy(activeLearner = cleaned) }
        }
        _homeState.update {
          it.copy(
            syncStatus = SheetSyncStatus.CONNECTED,
            syncMessage = "Auto-saved ${grade.shortLabel} to Teacher Sheet (Row $rowNum)",
          )
        }
      }.onFailure { err ->
        _homeState.update {
          it.copy(
            syncStatus = SheetSyncStatus.ERROR,
            syncMessage = "Offline/Queue: ${err.localizedMessage ?: "Retry sync"}",
          )
        }
      }
      return
    }

    val token = accessToken ?: return
    var handle = spreadsheetHandle
    if (handle == null) {
      val meta = recordsStore.cloudMetaFlow.first()
      handle = runCatching {
        sheetsRepo.findOrCreateSpreadsheet(token, meta.spreadsheetId, meta.connectedEmail)
      }.getOrNull()?.also { spreadsheetHandle = it }
    }
    if (handle == null) return

    _homeState.update { it.copy(syncStatus = SheetSyncStatus.SYNCING, syncMessage = "Saving to Google Sheets (${grade.shortLabel})...") }
    runCatching {
      val assignedRow = sheetsRepo.upsertLearnerRow(token, handle, record, grade)
      val gp = record.progressFor(grade)
      val syncedRecords = gp.records.mapValues { (_, rec) -> rec.copy(syncedToSheet = true) }
      val cleanedGp = gp.copy(
        records = syncedRecords,
        pendingSheetSync = false,
        sheetRowNumber = assignedRow,
      )
      val cleaned = record.copy(
        profile = if (grade == AralGrade.GRADE_4) record.profile.copy(sheetRowNumber = assignedRow) else record.profile,
      ).withGradeProgress(grade, cleanedGp)
      recordsStore.upsertLearner(cleaned)
      cleaned to assignedRow
    }.onSuccess { (cleaned, rowNum) ->
      if (_homeState.value.activeLearner?.profile?.normalizedKey == cleaned.profile.normalizedKey) {
        _homeState.update { it.copy(activeLearner = cleaned) }
      }
      _homeState.update {
        it.copy(
          syncStatus = SheetSyncStatus.CONNECTED,
          syncMessage = "Saved ${grade.shortLabel} to Google Sheets (Row $rowNum)",
        )
      }
    }.onFailure { err ->
      _homeState.update {
        it.copy(
          syncStatus = SheetSyncStatus.ERROR,
          syncMessage = "Offline/Queue: ${err.localizedMessage ?: "Retry sync"}",
        )
      }
    }
  }

  private fun currentTimestampString(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US)
    return sdf.format(Date())
  }
}
