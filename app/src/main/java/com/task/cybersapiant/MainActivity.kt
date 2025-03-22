package com.task.cybersapiant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.task.cybersapiant.ui.NavHostScreen
import com.task.cybersapiant.ui.task.settings.SettingsViewModel
import com.task.cybersapiant.utils.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val settingsViewModel: SettingsViewModel = hiltViewModel()
            val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()

            MyAppTheme(isDarkTheme) {
                NavHostScreen(settingsViewModel)
            }
        }
    }
}



