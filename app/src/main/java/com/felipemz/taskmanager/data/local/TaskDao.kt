package com.felipemz.taskmanager.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: TaskEntity)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks WHERE isSynced = 0")
    fun getPendingTasks(): Flow<List<TaskEntity>>

    @Query("UPDATE tasks SET isSynced = 1 WHERE id = :id")
    suspend fun markAsSynced(id: Int)
}