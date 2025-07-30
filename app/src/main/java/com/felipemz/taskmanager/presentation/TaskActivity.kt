package com.felipemz.taskmanager.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.felipemz.taskmanager.presentation.mvi.TaskIntent
import com.felipemz.taskmanager.presentation.mvi.TaskViewModel
import com.felipemz.taskmanager.presentation.ui.TaskScreen
import com.felipemz.taskmanager.presentation.ui.theme.TaskManagerTheme
import com.felipemz.taskmanager.work.SyncScheduler
import dagger.hilt.android.AndroidEntryPoint

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
