package com.example.taskmanagement.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.taskmanagement.model.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskManagementDb : RoomDatabase() {
	abstract fun taskEntityDao() : TaskEntityDao
}