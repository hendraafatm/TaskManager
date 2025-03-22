package com.task.cybersapiant.ui.utils

import android.graphics.Paint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp


@Composable
fun CustomProgressIndicator(progress: Float, textColor: Color, modifier: Modifier = Modifier) {
    // Animate the progress value to smoothly transition
    val animatedProgress by animateFloatAsState(
        targetValue = progress, animationSpec = tween(durationMillis = 500),
        label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(200.dp) // Size of the circle
            .padding(16.dp) // Optional padding
    ) {
        // Canvas to draw the circular progress indicator
        Canvas(modifier = Modifier.fillMaxSize()) {
            val size = size.minDimension // Use the smaller dimension to make a perfect circle
            val strokeWidth = 40f // Stroke width for the progress circle
            val center = Offset(size / 2, size / 2) // Center of the circle
            val radius = size / 2 - strokeWidth / 2 // Radius considering the stroke width

            // Draw the background circle (the uncompleted part)
            drawCircle(
                color = Color.Gray.copy(alpha = 0.3f), // Light gray background
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth)
            )

            // Draw the progress circle (the completed part)
            drawArc(
                color = Color.Green, // Green color for the completed progress
                startAngle = -90f, // Start angle at the top (12 o'clock position)
                sweepAngle = 360 * animatedProgress, // Sweep angle proportional to progress
                useCenter = false, // Don't fill the center, just the arc
                size = Size(radius * 2, radius * 2),
                topLeft = Offset(center.x - radius, center.y - radius),
                style = Stroke(width = strokeWidth)
            )

            // Draw the percentage text in the center of the circle
            drawContext.canvas.nativeCanvas.apply {
                drawText(

                    "${(animatedProgress * 100).toInt()}%", // Text showing the percentage
                    center.x,
                    center.y,
                    Paint().apply {
                        color = textColor.toArgb()
                        textAlign = Paint.Align.CENTER
                        textSize = 40f
                        isAntiAlias = true
                    }
                )
            }
        }
    }
}
