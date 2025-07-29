package com.felipemz.taskmanager.domain.repository

import com.felipemz.taskmanager.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task)
    fun getTasks(): Flow<List<Task>>
    fun getPendingTasks(): Flow<List<Task>>
    suspend fun markTaskAsSynced(taskId: Int)
}