package com.task.cybersapiant.domain.usecase

import com.task.cybersapiant.data.model.DataTask
import com.task.cybersapiant.domain.repository.TaskRepository
import javax.inject.Inject

class TaskListUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend fun execute(): List<DataTask> {
        return repository.fetchTasks()
    }
}