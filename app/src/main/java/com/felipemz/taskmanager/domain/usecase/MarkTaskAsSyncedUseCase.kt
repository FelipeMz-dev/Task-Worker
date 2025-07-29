package com.felipemz.taskmanager.domain.usecase

import com.felipemz.taskmanager.domain.repository.TaskRepository

class MarkTaskAsSyncedUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Int) {
        repository.markTaskAsSynced(id)
    }
}