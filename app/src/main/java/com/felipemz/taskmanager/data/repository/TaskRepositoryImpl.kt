package com.felipemz.taskmanager.data.repository

import com.felipemz.taskmanager.data.local.TaskDao
import com.felipemz.taskmanager.data.local.TaskEntity
import com.felipemz.taskmanager.domain.model.Task
import com.felipemz.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val dao: TaskDao
) : TaskRepository {
    override suspend fun addTask(task: Task) {
        dao.insert(task.toEntity())
    }

    override fun getTasks(): Flow<List<Task>> =
        dao.getAllTasks().map { list -> list.map { it.toDomain() } }

    override fun getPendingTasks(): Flow<List<Task>> =
        dao.getPendingTasks().map { list -> list.map { it.toDomain() } }

    override suspend fun markTaskAsSynced(taskId: Int) {
        dao.markAsSynced(taskId)
    }

    private fun Task.toEntity() = TaskEntity(id, title, due, isSynced)

    private fun TaskEntity.toDomain() = Task(id, title, due, isSynced)
}