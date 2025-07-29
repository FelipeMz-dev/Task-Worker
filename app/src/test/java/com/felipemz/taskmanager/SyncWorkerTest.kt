package com.felipemz.taskmanager

import com.felipemz.taskmanager.domain.model.Task
import com.felipemz.taskmanager.domain.usecase.TaskSyncUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SyncWorkerTest {

    private lateinit var repository: FakeTaskRepository
    private lateinit var syncUseCase: TaskSyncUseCase

    @Before
    fun setup() {
        repository = FakeTaskRepository()
        syncUseCase = TaskSyncUseCase(repository)

        repository.addedTasks.add(Task(id = 1, title = "Tarea A", due = "2025-08-01"))
        repository.addedTasks.add(Task(id = 2, title = "Tarea B", due = "2025-08-02"))
    }

    @Test
    fun `syncs only unsynced tasks`() = runTest {
        syncUseCase.syncPendingTasks()

        assertEquals(listOf(1, 2), repository.syncedTasks)
        assertTrue(repository.addedTasks.all { it.isSynced })
    }
}
