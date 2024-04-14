package com.example.taskmanagement.mapper

import com.example.taskmanagement.model.TaskEntity
import com.example.taskmanagement.model.TaskModel
import javax.inject.Inject

class ModelMapper @Inject constructor() : BaseModelMapper<TaskEntity, TaskModel> {
	
	override fun convertEntityToModel(taskEntity : TaskEntity) : TaskModel {
		return TaskModel(taskEntity.id, taskEntity.title, taskEntity.desc, taskEntity.status)
	}
	
	override fun convertModelToEntity(taskModel : TaskModel) : TaskEntity {
		return TaskEntity(title = taskModel.title, desc = taskModel.description,
		                  status = taskModel.status)
	}
	
	override fun convertEntityToModelList(entityiList : List<TaskEntity>) : List<TaskModel> {
		val taskModelList : ArrayList<TaskModel> = arrayListOf()
		if (!entityiList.isNullOrEmpty()) {
			for (taskEntity in entityiList) {
				taskModelList.add(
					TaskModel(taskEntity.id, taskEntity.title, taskEntity.desc, taskEntity.status))
			}
		}
		return taskModelList
	}
	
	override fun convertModelToEntityList(taskModelList : List<TaskModel>) : List<TaskEntity> {
		val taskEntityList : ArrayList<TaskEntity> = arrayListOf()
		if (!taskModelList.isNullOrEmpty()) {
			for (taskModel in taskModelList) {
				taskEntityList.add(TaskEntity(title = taskModel.title, desc = taskModel.description,
				                              status = taskModel.status))
			}
		}
		return taskEntityList
	}
}