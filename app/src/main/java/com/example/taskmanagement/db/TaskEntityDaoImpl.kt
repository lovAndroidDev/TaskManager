package com.example.taskmanagement.db

import com.example.taskmanagement.mapper.ModelMapper
import com.example.taskmanagement.model.TaskModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskEntityDaoImpl @Inject constructor(private val taskDao : TaskEntityDao,
                                            private val mapper : ModelMapper) :
		TaskEntityDataSource {
	
	override suspend fun insertTask(taskModel : TaskModel) {
		withContext(Dispatchers.IO) {
			val taskEntity = mapper.convertModelToEntity(taskModel)
			taskDao.insertTaskInfo(taskEntity)
		}
	}
	
	override suspend fun getTaskList() : List<TaskModel>? {
		var taskList : List<TaskModel>? = listOf()
		withContext(Dispatchers.IO) {
			taskList = taskDao.getTaskList()?.let { mapper.convertEntityToModelList(it) }
		}
		return taskList
	}
	
	override suspend fun getFilteredTaskList(status : Int) : List<TaskModel>? {
		var taskList : List<TaskModel>? = listOf()
		withContext(Dispatchers.IO) {
			taskList = taskDao.getFilteredTaskList(status)
					?.let { mapper.convertEntityToModelList(it) }
		}
		return taskList
	}
	
	override suspend fun updateTask(id : Int,
	                                taskTitle : String,
	                                taskDesc : String,
	                                taskStatus : Int) {
		withContext(Dispatchers.IO) {
			taskDao.updateTaskInfo(id, taskTitle, taskDesc, taskStatus)
		}
	}
	
	override suspend fun deleteTask(id : Int) : List<TaskModel>? {
		var taskList : List<TaskModel>? = listOf()
		withContext(Dispatchers.IO) {
			taskDao.deleteTask(id)
			taskList = taskDao.getTaskList()?.let { mapper.convertEntityToModelList(it) }
		}
		return taskList
	}
}