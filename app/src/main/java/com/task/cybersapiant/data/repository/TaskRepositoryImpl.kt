package com.task.cybersapiant.data.repository

import com.task.cybersapiant.data.local.TaskDao
import com.task.cybersapiant.data.model.DataTask
import com.task.cybersapiant.domain.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
) : TaskRepository {

    override suspend fun insertTask(task: DataTask): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                taskDao.insertTask(task)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    override suspend fun fetchTasks(): List<DataTask> {
        return withContext(Dispatchers.IO) {
            try {
                taskDao.getTasks()
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override suspend fun deleteTask(task: DataTask): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                taskDao.deleteTask(task)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    override suspend fun completeTask(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                taskDao.updateTaskCompletion(taskId = id)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}