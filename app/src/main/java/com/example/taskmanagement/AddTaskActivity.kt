package com.example.taskmanagement

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.taskmanagement.interfaces.ITaskAddListener
import com.example.taskmanagement.model.TaskModel
import com.example.taskmanagement.ui.view.AddTaskScreenView
import com.example.taskmanagement.ui.view.BottomBarView
import com.example.taskmanagement.ui.view.TopAppBarView
import com.example.taskmanagement.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskActivity : ComponentActivity(), ITaskAddListener {
	
	private val taskViewModel : TaskViewModel by viewModels()
	private var taskModel : TaskModel? = null
	
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		getIntentData()
		setContent {
			Scaffold(topBar = {
				TopAppBarView(this)
			}, bottomBar = { BottomBarView(clickListener = this, taskModel?._id ?: 0) }, content = {
				Modifier.padding(it)
				AddTaskScreenView(taskModel)
			})
		}
	}
	
	private fun getIntentData() {
		if (intent != null && intent.extras != null) {
			val data = intent.extras
			taskModel = data?.get("TaskModel") as TaskModel
		} else {
			taskModel = TaskModel(title = "", description = "", status = 0)
		}
	}
	
	override fun addTaskClicked(taskModel : TaskModel?) {
		if (taskModel?._id != null && taskModel._id != 0) {
			taskModel.let { taskViewModel.updateTaskInfo(it) }
			Toast.makeText(this, "Task updated successfully!", Toast.LENGTH_SHORT).show()
			finish()
		} else {
			if (taskModel != null) {
				taskViewModel.createNewTask(taskModel)
				Toast.makeText(this, "Task created successfully!", Toast.LENGTH_SHORT).show()
				finish()
			}
		}
	}
	
	override fun close() {
		finish()
	}
}