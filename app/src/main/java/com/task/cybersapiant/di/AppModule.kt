package com.task.cybersapiant.di

import android.content.Context
import androidx.room.Room
import com.task.cybersapiant.data.local.AppDatabase
import com.task.cybersapiant.data.local.TaskDao
import com.task.cybersapiant.data.repository.TaskRepositoryImpl
import com.task.cybersapiant.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext app: Context): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "tasks_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTasksDao(db: AppDatabase): TaskDao {
        return db.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao
    ): TaskRepository {
        return TaskRepositoryImpl(taskDao = taskDao)
    }
}
