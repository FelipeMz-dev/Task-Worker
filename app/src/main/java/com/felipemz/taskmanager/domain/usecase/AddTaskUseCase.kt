package com.felipemz.taskmanager.domain.usecase

import com.felipemz.taskmanager.domain.model.Task
import com.felipemz.taskmanager.domain.repository.TaskRepository

class AddTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        repository.addTask(task)
    }
}