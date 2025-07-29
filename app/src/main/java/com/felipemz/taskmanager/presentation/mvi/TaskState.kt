package com.felipemz.taskmanager.presentation.mvi

import com.felipemz.taskmanager.domain.model.Task

data class TaskState(
    val tasks: List<Task> = emptyList(),
    val showDialog: Boolean = false,
)