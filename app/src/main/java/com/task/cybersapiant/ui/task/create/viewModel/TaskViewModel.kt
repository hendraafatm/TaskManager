package com.task.cybersapiant.ui.task.create.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.cybersapiant.data.model.DataTask
import com.task.cybersapiant.domain.usecase.TaskInsertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskInsertUseCase: TaskInsertUseCase
) : ViewModel() {

    private val _taskAdded = MutableStateFlow(false)
    val taskAdded: StateFlow<Boolean> = _taskAdded.asStateFlow()

    private val _shouldRefresh = MutableStateFlow(false)
    val shouldRefresh: StateFlow<Boolean> = _shouldRefresh.asStateFlow()

    fun insertTask(task: DataTask) {
        viewModelScope.launch {
            val isAdded = taskInsertUseCase.execute(task)
            Log.d("TaskUpdate", "inserted $isAdded")
            _taskAdded.value = isAdded
        }
    }

    fun resetTaskAdded() {
        _taskAdded.value = false
        setShouldRefresh(true)
    }


    fun setShouldRefresh(refresh: Boolean) {
        _shouldRefresh.value = refresh
    }
}

