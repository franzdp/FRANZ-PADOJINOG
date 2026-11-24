package com.example.data

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.identity.AuthorizationRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope

object GoogleSheetsScopes {
  val REQUIRED_SCOPES = listOf(
    Scope("https://www.googleapis.com/auth/spreadsheets"),
    Scope("https://www.googleapis.com/auth/drive.file"),
    Scope("https://www.googleapis.com/auth/userinfo.email"),
  )
}

tailrec fun Context.findActivity(): Activity? = when (this) {
  is Activity -> this
  is ContextWrapper -> baseContext.findActivity()
  else -> null
}

class GoogleAuthController(
  private val context: Context,
  private val launcher: ManagedActivityResultLauncher<IntentSenderRequest, ActivityResult>,
  private val onTokenSuccess: (String) -> Unit,
  private val onError: (String) -> Unit,
) {
  fun requestAuthorization(interactive: Boolean = true) {
    val activity = context.findActivity()
    if (activity == null) {
      onError("Unable to open Google Sign-In from current screen.")
      return
    }
    val request = AuthorizationRequest.builder()
      .setRequestedScopes(GoogleSheetsScopes.REQUIRED_SCOPES)
      .build()

    Identity.getAuthorizationClient(activity)
      .authorize(request)
      .addOnSuccessListener { result ->
        if (result.hasResolution()) {
          if (interactive) {
            val pendingIntent = result.pendingIntent
            if (pendingIntent != null) {
              launcher.launch(IntentSenderRequest.Builder(pendingIntent.intentSender).build())
            } else {
              onError("Google authorization needs user approval, but no prompt was provided.")
            }
          }
        } else {
          val token = result.accessToken
          if (!token.isNullOrBlank()) {
            onTokenSuccess(token)
          } else if (interactive) {
            onError("Google did not return an access token. Please try again.")
          }
        }
      }
      .addOnFailureListener { exc ->
        if (interactive) {
          onError(exc.localizedMessage ?: "Google Sheets authorization failed.")
        }
      }
  }
}

@Composable
fun rememberGoogleAuthController(
  onTokenSuccess: (String) -> Unit,
  onError: (String) -> Unit,
): GoogleAuthController {
  val context = LocalContext.current
  val launcher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartIntentSenderForResult(),
  ) { activityResult ->
    val activity = context.findActivity()
    if (activity == null) {
      onError("Google sign-in context lost.")
      return@rememberLauncherForActivityResult
    }
    try {
      val authResult = Identity.getAuthorizationClient(activity)
        .getAuthorizationResultFromIntent(activityResult.data)
      val token = authResult.accessToken
      if (!token.isNullOrBlank()) {
        onTokenSuccess(token)
      } else {
        onError("Authorization cancelled or no access token granted.")
      }
    } catch (e: ApiException) {
      val msg = if (activityResult.resultCode == Activity.RESULT_CANCELED) {
        "Google Sheets sign-in was cancelled."
      } else {
        "Google Sign-In failed (code ${e.statusCode}). Make sure a Google account is signed in on this device."
      }
      onError(msg)
    } catch (e: Exception) {
      onError(e.localizedMessage ?: "Unexpected Google Sign-In error.")
    }
  }

  return remember(context, launcher) {
    GoogleAuthController(
      context = context,
      launcher = launcher,
      onTokenSuccess = onTokenSuccess,
      onError = onError,
    )
  }
}
