package com.felipemz.taskmanager.presentation.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipemz.taskmanager.domain.model.Task
import com.felipemz.taskmanager.domain.usecase.AddTaskUseCase
import com.felipemz.taskmanager.domain.usecase.ObserveTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val observeTasksUseCase: ObserveTasksUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskState())
    val state: StateFlow<TaskState> = _state.asStateFlow()

    fun handleIntent(intent: TaskIntent) {
        when (intent) {
            is TaskIntent.Add -> addTask(intent.task)
            is TaskIntent.LoadTasks -> loadAllTask()
            is TaskIntent.ShowAndHideDialog -> {
                _state.value = _state.value.copy(showDialog = intent.show)
            }
        }
    }

    private fun loadAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            observeTasksUseCase().collect { tasks ->
                _state.value = _state.value.copy(tasks = tasks)
            }
        }
    }


    private fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            addTaskUseCase(task)
        }
    }
}
