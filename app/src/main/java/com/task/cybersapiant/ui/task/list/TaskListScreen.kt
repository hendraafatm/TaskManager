package com.task.cybersapiant.ui.task.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.navigation.NavController
import com.task.cybersapiant.ui.task.create.viewModel.TaskViewModel
import com.task.cybersapiant.ui.task.list.emptystate.EmptyState
import com.task.cybersapiant.ui.task.list.item.TaskItem
import com.task.cybersapiant.ui.task.list.viewModel.FilterStatus
import com.task.cybersapiant.ui.task.list.viewModel.SortOrder
import com.task.cybersapiant.ui.task.list.viewModel.TaskListViewModel
import com.task.cybersapiant.ui.task.settings.SettingsViewModel
import com.task.cybersapiant.ui.theme.AppTypography
import com.task.cybersapiant.ui.theme.Dimens
import com.task.cybersapiant.ui.utils.BouncingFloatingActionButton
import com.task.cybersapiant.ui.utils.CustomDropdownMenu
import com.task.cybersapiant.ui.utils.CustomProgressIndicator
import com.task.cybersapiant.ui.utils.ShimmerEffect
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    navController: NavController,
    viewModel: TaskListViewModel,
    insertTaskViewModel: TaskViewModel,
    settingsViewModel: SettingsViewModel
) {
    val isDarkTheme by settingsViewModel.isDarkTheme.collectAsState()
    val colors = if (isDarkTheme) darkColorScheme() else lightColorScheme()

    val allTasks by viewModel.allTasks.collectAsState()
    val tasks by viewModel.tasks.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val sortOrder by viewModel.sortOrder.collectAsState()
    val filterStatus by viewModel.filterStatus.collectAsState()
    val completedTasks = allTasks.count { it.isCompleted }
    val progress = if (allTasks.isNotEmpty()) completedTasks.toFloat() / allTasks.size else 0f
    val hapticFeedback = LocalHapticFeedback.current

    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val taskAdded by insertTaskViewModel.taskAdded.collectAsState()
    val shouldRefresh by insertTaskViewModel.shouldRefresh.collectAsState()

    val reorderState = rememberReorderableLazyListState(
        onMove = { from, to -> viewModel.reorderTasks(from.index, to.index) }
    )

    LaunchedEffect(taskAdded) {
        if (taskAdded) viewModel.fetchTasks()
    }
    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            viewModel.fetchTasks()
            insertTaskViewModel.setShouldRefresh(false)
        }
    }

    MaterialTheme(colorScheme = colors, typography = AppTypography) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Task List", style = AppTypography.titleLarge) },
                    actions = {
                        IconButton(onClick = { navController.navigate("settings") }) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            floatingActionButton = {
                BouncingFloatingActionButton(
                    onClick = { navController.navigate("create_task") }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(Dimens.ScreenPadding)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomDropdownMenu(
                        sortOrder,
                        SortOrder.values().toList(),
                        viewModel::setSortOrder
                    )
                    CustomDropdownMenu(
                        filterStatus,
                        FilterStatus.values().toList(),
                        viewModel::setFilterStatus
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.ProgressIndicatorPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CustomProgressIndicator(progress = progress, textColor = colors.secondary)
                        Spacer(modifier = Modifier.height(Dimens.ItemSpacing))
                        Text(
                            "Completed Tasks Percentage",
                            style = AppTypography.bodyLarge
                        )
                    }
                }

                if (loading) {
                    ShimmerEffect()
                } else {
                    if (tasks.isEmpty()) {
                        EmptyState()
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .reorderable(reorderState),
                            state = reorderState.listState,
                            verticalArrangement = Arrangement.spacedBy(Dimens.ItemSpacing)
                        ) {
                            items(tasks, key = { it.id }) { task ->
                                ReorderableItem(reorderState, key = task.id) {
                                    TaskItem(
                                        task = task,
                                        onClick = { navController.navigate("task_detail/${task.id}") },
                                        onDelete = {
                                            viewModel.deleteTask(task)
                                            scope.launch {
                                                val result = snackBarHostState.showSnackbar(
                                                    message = "Task deleted",
                                                    actionLabel = "Undo",
                                                    duration = SnackbarDuration.Short
                                                )
                                                if (result == SnackbarResult.ActionPerformed) {
                                                    insertTaskViewModel.insertTask(task)
                                                }
                                            }
                                        },
                                        onComplete = { viewModel.toggleTaskCompletion(task) },
                                        hapticFeedback = hapticFeedback,
                                        reorderState = reorderState,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}






