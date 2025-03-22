package com.task.cybersapiant.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.task.cybersapiant.ui.task.create.CreateTaskScreen
import com.task.cybersapiant.ui.task.create.viewModel.TaskViewModel
import com.task.cybersapiant.ui.task.details.TaskDetailScreen
import com.task.cybersapiant.ui.task.list.TaskListScreen
import com.task.cybersapiant.ui.task.list.viewModel.TaskListViewModel
import com.task.cybersapiant.ui.task.settings.SettingsScreen
import com.task.cybersapiant.ui.task.settings.SettingsViewModel

@Composable
fun NavHostScreen(settingsViewModel: SettingsViewModel) {
    val navController = rememberNavController()

    val taskListViewModel: TaskListViewModel = hiltViewModel()
    val taskViewModel: TaskViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = "task_list"
    ) {
        // Task List Screen
        composable("task_list") {
            TaskListScreen(navController, taskListViewModel, taskViewModel, settingsViewModel)
        }

        // Create Task Screen
        composable("create_task") {
            CreateTaskScreen(navController = navController, taskViewModel)
        }

        // Task Detail Screen
        composable("task_detail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            if (taskId != null) {
                TaskDetailScreen(taskId.toInt(), taskListViewModel, navController)
            }
        }

        // Settings Screen
        composable("settings") {
            SettingsScreen(navController = navController, settingsViewModel = settingsViewModel)
        }
    }
}



