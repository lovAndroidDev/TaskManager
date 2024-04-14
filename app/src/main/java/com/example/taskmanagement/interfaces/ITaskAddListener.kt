package com.example.taskmanagement.interfaces

import com.example.taskmanagement.model.TaskModel

interface ITaskAddListener {
	fun addTaskClicked(taskModel : TaskModel?)
	fun close()
}