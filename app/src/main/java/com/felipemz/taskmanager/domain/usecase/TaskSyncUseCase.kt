package com.felipemz.taskmanager.domain.usecase

import com.felipemz.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

class TaskSyncUseCase(private val repository: TaskRepository) {

    suspend fun syncPendingTasks() {
        repository.getPendingTasks().first().forEach { task ->
            delay(500)
            println("Enviando tarea: ${task.title}")
            repository.markTaskAsSynced(task.id)
        }
    }
}