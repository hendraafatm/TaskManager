package com.task.cybersapiant.utils

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

fun getPriorityColor(priority: String, colors: ColorScheme): Color {
    return when (priority) {
        "High" -> Color.Red // Red for High priority
        "Medium" -> Color(0xFFFFA500) // Orange for Medium priority
        "Low" -> Color(0xFF32CD32) // Green for Low priority
        else -> colors.onSurfaceVariant // Default color if the priority is not recognized
    }
}