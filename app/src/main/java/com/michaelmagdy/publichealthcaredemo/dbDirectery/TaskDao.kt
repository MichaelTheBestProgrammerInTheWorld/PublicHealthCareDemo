package com.michaelmagdy.publicHealthCareDemo.dbDirectery

import androidx.room.*

@Dao
interface TaskDao {

    @Insert
   suspend  fun inserttaskItem(taskItem: TaskItem)

    @Query("SELECT * FROM taskItem ORDER BY id DESC")
    suspend fun getAllTask(): List<TaskItem>
    @Delete
    suspend fun deletitem(id: TaskItem)
    @Update
    suspend fun updatetak(taskItem: TaskItem)
}