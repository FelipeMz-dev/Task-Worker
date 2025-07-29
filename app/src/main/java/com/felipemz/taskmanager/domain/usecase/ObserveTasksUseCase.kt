package com.felipemz.taskmanager.domain.usecase

import com.felipemz.taskmanager.domain.model.Task
import com.felipemz.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class ObserveTasksUseCase(private val repository: TaskRepository) {
    operator fun invoke(): Flow<List<Task>> = repository.getTasks()
}