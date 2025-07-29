package com.felipemz.taskmanager.di

import android.content.Context
import androidx.room.Room
import com.felipemz.taskmanager.data.local.TaskDao
import com.felipemz.taskmanager.data.local.TaskDatabase
import com.felipemz.taskmanager.data.repository.TaskRepositoryImpl
import com.felipemz.taskmanager.domain.repository.TaskRepository
import com.felipemz.taskmanager.domain.usecase.AddTaskUseCase
import com.felipemz.taskmanager.domain.usecase.MarkTaskAsSyncedUseCase
import com.felipemz.taskmanager.domain.usecase.ObservePendingTasksUseCase
import com.felipemz.taskmanager.domain.usecase.ObserveTasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase =
        Room.databaseBuilder(context, TaskDatabase::class.java, "tasks.db").build()

    @Provides
    fun provideTaskDao(db: TaskDatabase): TaskDao = db.taskDao()

    @Provides
    fun provideRepository(dao: TaskDao): TaskRepository = TaskRepositoryImpl(dao)

    //Use Cases
    @Provides fun provideAddTaskUseCase(repo: TaskRepository) = AddTaskUseCase(repo)
    @Provides fun provideObservePendingTasksUseCase(repo: TaskRepository) = ObservePendingTasksUseCase(repo)
    @Provides fun provideObserveTasksUseCase(repo: TaskRepository) = ObserveTasksUseCase(repo)
    @Provides fun provideMarkSyncedUseCase(repo: TaskRepository) = MarkTaskAsSyncedUseCase(repo)
}
