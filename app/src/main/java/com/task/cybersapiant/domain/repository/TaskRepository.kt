package com.task.cybersapiant.domain.repository

import com.task.cybersapiant.data.model.DataTask
import java.util.concurrent.Flow

interface TaskRepository {
    suspend fun insertTask(task : DataTask) : Boolean
    suspend fun fetchTasks() : List<DataTask>
    suspend fun deleteTask(task: DataTask) : Boolean
    suspend fun completeTask(id : Int) : Boolean
//    suspend fun getTaskById(taskId: Int): DataTask?
}