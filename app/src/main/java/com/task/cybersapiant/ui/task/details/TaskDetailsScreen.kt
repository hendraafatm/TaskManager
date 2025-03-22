package com.task.cybersapiant.ui.task.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.task.cybersapiant.ui.task.list.viewModel.TaskListViewModel
import com.task.cybersapiant.ui.theme.Dimens
import com.task.cybersapiant.utils.getPriorityColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Int,
    viewModel: TaskListViewModel,
    navController: NavController
) {
    val task = viewModel.getTaskById(taskId)
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    task?.let { _task ->
        val priorityColor = getPriorityColor(_task.priority, colors)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Task Details", style = typography.titleLarge) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)  // Apply padding from Scaffold
                    .padding(Dimens.ScreenPadding) // Custom padding based on dimension
            ) {
                // Task Title
                Text(
                    text = _task.title,
                    style = typography.headlineLarge.copy(color = colors.onSurface),
                    modifier = Modifier.padding(bottom = Dimens.ItemSpacing) // Add padding below the title
                )

                // Task Description
                Text(
                    text = _task.description ?: "No description",
                    style = typography.bodyLarge.copy(color = colors.onSurface),
                    modifier = Modifier.padding(bottom = Dimens.ItemSpacing) // Add padding below description
                )

                // Priority Label
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Priority:",
                        style = typography.bodyLarge.copy(color = colors.onSurfaceVariant)
                    )
                    Text(
                        text = _task.priority,
                        style = typography.bodyLarge.copy(color = priorityColor)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.ContentPadding))

                // Due Date Label
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Due Date:",
                        style = typography.bodyLarge.copy(color = colors.onSurfaceVariant)
                    )
                    Text(
                        text = _task.dueDate,
                        style = typography.bodyLarge.copy(color = colors.primary)
                    )
                }

                Spacer(modifier = Modifier.height(Dimens.ContentPadding))

                // Show "Mark as Complete" button only if the task is not completed
                if (!_task.isCompleted) {
                    Button(
                        onClick = {
                            viewModel.toggleTaskCompletion(_task)
                            navController.popBackStack()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Mark as Complete",
                            style = typography.titleMedium.copy(color = Color.White)
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimens.ContentPadding))
                }

                // "Delete" button
                Button(
                    onClick = {
                        viewModel.deleteTask(_task)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Delete",
                        style = typography.titleMedium.copy(color = Color.White)
                    )
                }
            }
        }
    } ?: run {
        // Loading state
        Text(
            text = "No Task Found !...",
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center,
            style = typography.bodyLarge
        )
    }
}


