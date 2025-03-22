package com.task.cybersapiant.domain.usecase

import com.task.cybersapiant.domain.repository.TaskRepository
import javax.inject.Inject

class TaskCompleteUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend fun execute(id: Int): Boolean {
        return repository.completeTask(id)
    }
}