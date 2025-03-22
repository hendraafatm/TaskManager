package com.task.cybersapiant.ui.task.list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.cybersapiant.data.model.DataTask
import com.task.cybersapiant.domain.usecase.TaskCompleteUseCase
import com.task.cybersapiant.domain.usecase.TaskDeleteUseCase
//import com.task.cybersapiant.domain.usecase.TaskGetByIdUseCase
import com.task.cybersapiant.domain.usecase.TaskListUseCase
import com.task.cybersapiant.utils.priorities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskListUseCase: TaskListUseCase,
    private val taskDeleteUseCase: TaskDeleteUseCase,
    private val taskCompleteUseCase: TaskCompleteUseCase,
) : ViewModel() {

    private val _allTasks = MutableStateFlow<List<DataTask>>(emptyList()) // Holds all tasks
    val allTasks: StateFlow<List<DataTask>> = _allTasks.asStateFlow()

    private val _tasks = MutableStateFlow<List<DataTask>>(emptyList())
    val tasks: StateFlow<List<DataTask>> = _tasks.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _sortOrder = MutableStateFlow(SortOrder.BY_DATE)
    val sortOrder: StateFlow<SortOrder> = _sortOrder.asStateFlow()

    private val _filterStatus = MutableStateFlow(FilterStatus.ALL)
    val filterStatus: StateFlow<FilterStatus> = _filterStatus.asStateFlow()

    init {
        fetchTasks()
    }

    fun fetchTasks() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val allFetchedTasks = taskListUseCase.execute()
                _allTasks.value = allFetchedTasks

                val sortedTasks = sortTasks(allFetchedTasks, _sortOrder.value)
                val filteredTasks = filterTasks(sortedTasks, _filterStatus.value)
                _tasks.value = filteredTasks
            } catch (e: Exception) {
                _tasks.value = emptyList()
            } finally {
                _loading.value = false
            }
        }
    }

    fun reorderTasks(fromIndex: Int, toIndex: Int) {
        val mutableList = tasks.value.toMutableList()
        val movedTask = mutableList.removeAt(fromIndex)
        mutableList.add(toIndex, movedTask)
        _tasks.value = mutableList
    }

    fun deleteTask(task: DataTask) {
        viewModelScope.launch {
            taskDeleteUseCase.execute(task)
            fetchTasks()
        }
    }

    fun toggleTaskCompletion(task: DataTask) {
        viewModelScope.launch {
            taskCompleteUseCase.execute(task.id)
            fetchTasks()
        }
    }

    fun setSortOrder(order: SortOrder) {
        _sortOrder.value = order
        fetchTasks()
    }

    fun setFilterStatus(status: FilterStatus) {
        _filterStatus.value = status
        fetchTasks()
    }

    fun getTaskById(id: Int): DataTask? {
        return _allTasks.value.find { it.id == id }
    }

    private fun sortTasks(tasks: List<DataTask>, order: SortOrder): List<DataTask> {
        return when (order) {
            SortOrder.BY_PRIORITY -> tasks.sortedBy { priorities.indexOf(it.priority) }
            SortOrder.BY_DATE -> tasks.sortedBy { it.dueDate }
            SortOrder.ALPHABETICALLY -> tasks.sortedBy { it.title }
        }
    }


    private fun filterTasks(tasks: List<DataTask>, status: FilterStatus): List<DataTask> {
        return when (status) {
            FilterStatus.ALL -> tasks
            FilterStatus.COMPLETED -> tasks.filter { it.isCompleted }
            FilterStatus.PENDING -> tasks.filter { !it.isCompleted }
        }
    }
}

enum class SortOrder {
    BY_PRIORITY, BY_DATE, ALPHABETICALLY
}

enum class FilterStatus {
    ALL, COMPLETED, PENDING
}