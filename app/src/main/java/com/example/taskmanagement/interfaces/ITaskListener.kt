package com.example.taskmanagement.interfaces

import com.example.taskmanagement.model.TaskModel

interface ITaskListener {
	fun updateTask(taskModel : TaskModel?)
	fun deleteTask(taskID : Int)
	fun openAddTaskScreen()
}