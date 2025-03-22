package com.task.cybersapiant.ui.task.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.cybersapiant.data.local.ThemePreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themePreferences: ThemePreferences
) :
    ViewModel() {

    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> get() = _isDarkTheme

    init {
        viewModelScope.launch {
            _isDarkTheme.value = themePreferences.isDarkTheme.first()
        }
    }

    fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            themePreferences.setDarkTheme(isDark)
            _isDarkTheme.value = isDark
        }
    }
}


