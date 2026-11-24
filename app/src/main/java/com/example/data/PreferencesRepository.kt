package com.example.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "aral_math_prefs")

data class UserSettings(
  val showHintsByDefault: Boolean = false,
  val quickRoundDefault: Boolean = true,
)

class PreferencesRepository(private val context: Context) {
  private val showHintsKey = booleanPreferencesKey("show_hints_default")
  private val quickRoundKey = booleanPreferencesKey("quick_round_default")

  val settingsFlow: Flow<UserSettings> = context.dataStore.data.map { prefs ->
    UserSettings(
      showHintsByDefault = prefs[showHintsKey] ?: false,
      quickRoundDefault = prefs[quickRoundKey] ?: true,
    )
  }

  suspend fun setShowHintsByDefault(enabled: Boolean) {
    context.dataStore.edit { it[showHintsKey] = enabled }
  }

  suspend fun setQuickRoundDefault(enabled: Boolean) {
    context.dataStore.edit { it[quickRoundKey] = enabled }
  }
}
