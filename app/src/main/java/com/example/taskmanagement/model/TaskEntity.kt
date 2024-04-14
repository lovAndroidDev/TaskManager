package com.example.taskmanagement.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_entity")
data class TaskEntity(
		@PrimaryKey(autoGenerate = true)
		val id : Int = 0,
		
		@ColumnInfo("title")
		val title : String = "",
		
		@ColumnInfo("description")
		val desc : String = "",
		
		@ColumnInfo("status")
		val status : Int = 0)