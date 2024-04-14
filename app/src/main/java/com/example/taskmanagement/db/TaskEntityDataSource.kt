package com.example.taskmanagement.db

import com.example.taskmanagement.model.TaskModel

interface TaskEntityDataSource {
	
	suspend fun insertTask(taskModel : TaskModel)
	suspend fun getTaskList() : List<TaskModel>?
	suspend fun getFilteredTaskList(status : Int) : List<TaskModel>?
	suspend fun updateTask(id : Int, taskTitle : String, taskDesc : String, taskStatus : Int)
	suspend fun deleteTask(id : Int) : List<TaskModel>?
}