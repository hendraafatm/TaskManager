package com.task.cybersapiant.ui.task.create

import android.app.DatePickerDialog
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.task.cybersapiant.data.model.DataTask
import com.task.cybersapiant.ui.task.create.viewModel.TaskViewModel
import com.task.cybersapiant.ui.theme.Dimens
import com.task.cybersapiant.utils.priorities
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(navController: NavController, viewModel: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedPriority by remember { mutableStateOf(priorities[0]) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    var selectedDate by remember { mutableStateOf("") }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    val taskAdded by viewModel.taskAdded.collectAsState()

    // Observe taskAdded state
    LaunchedEffect(taskAdded) {
        if (taskAdded) {
            Log.d("TaskUpdate", "Added successfully")
            Toast.makeText(context, "Task added successfully!", Toast.LENGTH_LONG).show()
            viewModel.resetTaskAdded()  // Reset state
            navController.popBackStack()  // Navigate back
        }
    }

    // Track when the user clicks back without adding a task
    DisposableEffect(Unit) {
        onDispose {
            if (!taskAdded) {
                viewModel.setShouldRefresh(false)
            }
        }
    }

    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Task", style = typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->  // Get padding values from the Scaffold
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)  // Apply padding to the column
                .padding(horizontal = Dimens.ScreenPadding),  // Add padding at the bottom
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title", style = typography.labelLarge) },
                textStyle = typography.bodyLarge.copy(color = colors.onSurface),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colors.onSurface,
                    cursorColor = colors.primary,
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.onSurfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.ItemSpacing) // Consistent item spacing
            )

            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description", style = typography.labelLarge) },
                textStyle = typography.bodyLarge.copy(color = colors.onSurface),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colors.onSurface,
                    cursorColor = colors.primary,
                    focusedBorderColor = colors.primary,
                    unfocusedBorderColor = colors.onSurfaceVariant
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.ItemSpacing)
            )

            // Priority Dropdown
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimens.ItemSpacing)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = selectedPriority,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Priority", style = typography.labelLarge) },
                        textStyle = typography.bodyLarge.copy(color = colors.onSurface),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = colors.onSurface,
                            cursorColor = colors.primary,
                            focusedBorderColor = colors.primary,
                            unfocusedBorderColor = colors.onSurfaceVariant
                        ),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown",
                                modifier = Modifier.clickable { expanded = !expanded }
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        priorities.forEach { priority ->
                            DropdownMenuItem(
                                text = { Text(priority, style = typography.bodyMedium) },
                                onClick = {
                                    selectedPriority = priority
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // Date Picker
            Text(
                text = if (selectedDate.isNotEmpty()) "Due Date: $selectedDate" else "Select Due Date",
                modifier = Modifier
                    .clickable { datePickerDialog.show() }
                    .padding(Dimens.ItemSpacing),
                style = typography.bodyLarge.copy(color = colors.primary)
            )

            Spacer(modifier = Modifier.height(Dimens.ContentPadding))

            // Save Task Button
            Button(
                onClick = {
                    if (title.isNotEmpty()) {
                        val newTask = DataTask(
                            title = title,
                            description = description.ifEmpty { null },
                            priority = selectedPriority,
                            dueDate = selectedDate,
                            isCompleted = false
                        )
                        viewModel.insertTask(newTask)  // Save to database
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Create",
                    style = typography.titleMedium.copy(color = colors.onPrimary)
                )
            }
        }
    }
}

