package com.felipemz.taskmanager

import com.felipemz.taskmanager.domain.model.Task
import com.felipemz.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskRepository : TaskRepository {

    val addedTasks = mutableListOf<Task>()
    val syncedTasks = mutableListOf<Int>()

    override suspend fun addTask(task: Task) {
        addedTasks.add(task)
    }

    override fun getTasks(): Flow<List<Task>> {
        return flow { emit(addedTasks) }
    }

    override fun getPendingTasks(): Flow<List<Task>> {
        return flow { emit(addedTasks.filter { !it.isSynced }) }
    }

    override suspend fun markTaskAsSynced(taskId: Int) {
        syncedTasks.add(taskId)
        val index = addedTasks.indexOfFirst { it.id == taskId }
        if (index != -1) {
            addedTasks[index] = addedTasks[index].copy(isSynced = true)
        }
    }
}