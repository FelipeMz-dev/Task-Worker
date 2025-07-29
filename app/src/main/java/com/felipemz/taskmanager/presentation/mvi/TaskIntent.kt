package com.felipemz.taskmanager.presentation.mvi

import com.felipemz.taskmanager.domain.model.Task

sealed interface TaskIntent {
    data class Add(val task: Task) : TaskIntent
    data class ShowAndHideDialog(val show: Boolean) : TaskIntent
    object LoadTasks : TaskIntent
}