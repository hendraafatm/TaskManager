package com.task.cybersapiant.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemePreferences(private val dataStore: DataStore<Preferences>) {

    private val DARK_THEME_KEY = booleanPreferencesKey("dark_theme_key")

    val isDarkTheme: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_KEY] ?: false
        }

    suspend fun setDarkTheme(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDark
        }
    }
}



