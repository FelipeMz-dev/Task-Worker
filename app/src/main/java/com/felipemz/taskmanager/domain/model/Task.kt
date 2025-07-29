package com.felipemz.taskmanager.domain.model

data class Task(
    val id: Int = 0,
    val title: String,
    val due: String,
    val isSynced: Boolean = false
)
