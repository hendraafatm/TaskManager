package com.task.cybersapiant.ui.task.create

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import app.cash.paparazzi.Paparazzi
import com.task.cybersapiant.utils.MyAppTheme
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class CreateTaskScreenScreenshotTest {

    @get:Rule
    val paparazzi = Paparazzi()

    @Test
    fun captureCreateTaskScreen_LightMode() {
        paparazzi.snapshot {
            ThemedPreview(darkTheme = false) // Light Mode
        }
    }

    @Test
    fun captureCreateTaskScreen_DarkMode() {
        paparazzi.snapshot {
            ThemedPreview(darkTheme = true) // Dark Mode
        }
    }

    @Composable
    private fun ThemedPreview(darkTheme: Boolean) {
        MyAppTheme(isDarkTheme = darkTheme) {
            CreateTaskScreen(
                navController = rememberNavController(),
                viewModel = mockk(relaxed = true)
            )
        }
    }
}
