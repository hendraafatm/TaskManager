package com.task.cybersapiant.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.task.cybersapiant.data.model.DataTask

@Dao
interface TaskDao {
    @Insert
    suspend fun insertTask(task: DataTask)

    @Query("SELECT * FROM tasks")
    suspend fun getTasks(): List<DataTask>

    @Delete
    suspend fun deleteTask(task: DataTask)

    @Query("UPDATE tasks SET isCompleted = :isCompleted WHERE id = :taskId")
    suspend fun updateTaskCompletion(taskId: Int, isCompleted: Boolean = true)
}
