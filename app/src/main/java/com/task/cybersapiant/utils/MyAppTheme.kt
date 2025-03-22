package com.task.cybersapiant.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.task.cybersapiant.ui.theme.AppTypography
import com.task.cybersapiant.ui.theme.DarkColorScheme
import com.task.cybersapiant.ui.theme.LightColorScheme

@Composable
fun MyAppTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
