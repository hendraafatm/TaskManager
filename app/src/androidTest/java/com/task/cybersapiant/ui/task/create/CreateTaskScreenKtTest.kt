package com.task.cybersapiant.ui.task.create

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.task.cybersapiant.ui.task.create.viewModel.TaskViewModel
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CreateTaskScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockViewModel: TaskViewModel

    @Before
    fun setUp() {
        mockViewModel = mockk(relaxed = true)  // Create a relaxed mock

        // Ensure taskAdded is a MutableStateFlow so it can emit values
        every { mockViewModel.taskAdded } returns MutableStateFlow(false)
    }

    // Helper function to set up the screen
    private fun setUpCreateTaskScreen() {
        composeTestRule.setContent {
            CreateTaskScreen(navController = rememberNavController(), viewModel = mockViewModel)
        }
    }

    @Test
    fun test_uiElementsAreDisplayed() {
        setUpCreateTaskScreen()

        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
        composeTestRule.onNodeWithText("Description").assertIsDisplayed()
        composeTestRule.onNodeWithText("Priority").assertIsDisplayed()
        composeTestRule.onNodeWithText("Select Due Date").assertIsDisplayed()
        composeTestRule.onNodeWithText("Create").assertIsDisplayed()
    }

    @Test
    fun test_titleAndDescriptionInput() {
        setUpCreateTaskScreen()

        composeTestRule.onNodeWithText("Title").performTextInput("Task Title")
        composeTestRule.onNodeWithText("Description").performTextInput("Task Description")

        composeTestRule.onNode(
            hasTextExactly("Task Title", includeEditableText = true),
            useUnmergedTree = true
        ).assertExists("Title input is incorrect")

        composeTestRule.onNode(
            hasTextExactly("Task Description", includeEditableText = true),
            useUnmergedTree = true
        ).assertExists("Description input is incorrect")
    }

    @Test
    fun test_prioritySelection() {
        setUpCreateTaskScreen()

        // Open the dropdown menu
        composeTestRule.onNodeWithText("Priority").performClick()

        // Select "High" from the dropdown menu by ensuring it's not the already selected value
        composeTestRule.onNode(
            hasText("High") and hasClickAction(),
            useUnmergedTree = true  // Ensures targeting the dropdown item
        ).performClick()

        // Verify "High" is selected
        composeTestRule.onNodeWithText("Priority").assertTextContains("High")
    }


    @Test
    fun test_createButtonSavesTask() {
        // Mock task insertion and state reset
        every { mockViewModel.insertTask(any()) } just Runs
        every { mockViewModel.resetTaskAdded() } just Runs

        setUpCreateTaskScreen()

        composeTestRule.onNodeWithText("Title").performTextInput("Task Title")
        composeTestRule.onNodeWithText("Description").performTextInput("Task Description")

        // Open the dropdown menu
        composeTestRule.onNodeWithText("Priority").performClick()

        // Select "High" priority from the dropdown menu by ensuring it's not already in the field
        composeTestRule.onNode(
            hasText("High") and hasClickAction(),
            useUnmergedTree = true  // Ensures targeting the dropdown item, not the selected value
        ).performClick()

        // Click the Create button
        composeTestRule.onNodeWithText("Create").performClick()

        // Verify task insertion
        verify { mockViewModel.insertTask(any()) }
    }
}

