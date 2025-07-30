package com.felipemz.taskmanager.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipemz.taskmanager.domain.model.Task
import com.felipemz.taskmanager.presentation.mvi.TaskIntent
import com.felipemz.taskmanager.presentation.mvi.TaskViewModel
import com.felipemz.taskmanager.presentation.ui.TaskScreen
import com.felipemz.taskmanager.presentation.ui.theme.TaskManagerTheme
import com.felipemz.taskmanager.work.SyncScheduler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        SyncScheduler.enqueueSync(applicationContext)
        setContent {
            val state = viewModel.state.collectAsStateWithLifecycle().value

            TaskManagerTheme {
                TaskScreen(
                    state = state,
                    intentHandler = ::handleIntent
                )
            }
        }
    }

    private fun handleIntent(intent: TaskIntent) {
        viewModel.handleIntent(intent)
    }
}
