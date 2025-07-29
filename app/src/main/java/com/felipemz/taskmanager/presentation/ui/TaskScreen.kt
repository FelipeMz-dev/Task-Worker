package com.felipemz.taskmanager.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.felipemz.taskmanager.presentation.mvi.TaskIntent
import com.felipemz.taskmanager.presentation.mvi.TaskState
import com.felipemz.taskmanager.R
import com.felipemz.taskmanager.domain.model.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    state: TaskState,
    intentHandler: (TaskIntent) -> Unit,
) {

    LaunchedEffect(Unit) {
        intentHandler(TaskIntent.LoadTasks)
    }

    if (state.showDialog) TaskCreationDialog(
        onDismissRequest = { intentHandler(TaskIntent.ShowAndHideDialog(false)) },
        onTaskCreated = { task ->
            intentHandler(TaskIntent.Add(task))
        }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    intentHandler(TaskIntent.ShowAndHideDialog(true))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.tasks) { TaskItem(it) }
        }
    }
}

@Composable
private fun TaskItem(task: Task) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = task.title,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Fecha límite: ${task.due}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun TaskCreationDialog(
    onDismissRequest: () -> Unit,
    onTaskCreated: (Task) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var due by remember { mutableStateOf("") }

    Dialog(onDismissRequest) {
        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.large
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Crear Tarea",
                color = MaterialTheme.colorScheme.onSurface,
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") }
            )

            OutlinedTextField(
                value = due,
                onValueChange = { due = it },
                label = { Text("Fecha límite") }
            )

            Button(
                onClick = {
                    if (title.isNotBlank() && due.isNotBlank()) {
                        val task = Task(title = title, due = due)
                        onTaskCreated(task)
                        onDismissRequest()
                    }
                }
            ) { Text("Agregar") }
        }
    }
}