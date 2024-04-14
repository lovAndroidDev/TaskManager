package com.example.taskmanagement.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskmanagement.db.TaskEntityDataSource
import com.example.taskmanagement.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(private val taskDataSource : TaskEntityDataSource) :
		ViewModel() {
	
	private val _taskList = MutableStateFlow(listOf<TaskModel>())
	val taskList : StateFlow<List<TaskModel>>
		get() = _taskList
	
	private val _pendingTaskCount = MutableStateFlow(0)
	val pendingTaskCount : StateFlow<Int>
		get() = _pendingTaskCount
	
	private val _completedTaskCount = MutableStateFlow(0)
	val completedTaskCount : StateFlow<Int>
		get() = _completedTaskCount
	
	private val _cancelledTaskCount = MutableStateFlow(0)
	val cancelledTaskCount : StateFlow<Int>
		get() = _cancelledTaskCount
	
	fun createNewTask(task : TaskModel) {
		viewModelScope.launch {
			taskDataSource.insertTask(task)
		}
	}
	
	fun getTaskList() {
		viewModelScope.launch {
			taskDataSource.getTaskList()?.let {
				_taskList.value = it
			}
		}
	}
	
	fun getFilteredList(status : Int) {
		viewModelScope.launch {
			taskDataSource.getFilteredTaskList(status)?.let {
				if(status ==0) {
					_pendingTaskCount.value = it.size
				} else if(status == 1) {
					_completedTaskCount.value = it.size
				} else if(status == 2) {
					_cancelledTaskCount.value = it.size
				}
			}
		}
	}
	
	fun deleteTask(taskId : Int) {
		viewModelScope.launch {
			taskDataSource.deleteTask(taskId)?.let {
				_taskList.value = it
			}
		}
	}
	
	fun updateTaskInfo(task : TaskModel) {
		viewModelScope.launch {
			taskDataSource.updateTask(task._id, task.title, task.description, task.status)
		}
	}
}