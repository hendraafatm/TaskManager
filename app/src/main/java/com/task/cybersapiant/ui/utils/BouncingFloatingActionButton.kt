package com.task.cybersapiant.ui.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.launch

@Composable
fun BouncingFloatingActionButton(onClick: () -> Unit) {
    val scale = remember { Animatable(1f) }
    val coroutineScope = rememberCoroutineScope()

    FloatingActionButton(
        onClick = {
            coroutineScope.launch {
                scale.animateTo(0.85f, animationSpec = tween(100, easing = LinearOutSlowInEasing))
                scale.animateTo(1f, animationSpec = tween(100, easing = FastOutSlowInEasing))
            }
            onClick()
        },
        modifier = Modifier.scale(scale.value),
        contentColor = MaterialTheme.colorScheme.secondary,
        containerColor = MaterialTheme.colorScheme.primary,
        shape = CircleShape
    ) {
        Icon(Icons.Default.Add, tint = MaterialTheme.colorScheme.secondaryContainer, contentDescription = "Add Task")
    }
}

