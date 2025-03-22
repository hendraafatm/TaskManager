package com.task.cybersapiant.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.cybersapiant.data.model.DataTask

@Database(entities = [DataTask::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
