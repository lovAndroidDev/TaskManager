package com.example.taskmanagement

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.taskmanagement.interfaces.ITaskListener
import com.example.taskmanagement.model.TaskModel
import com.example.taskmanagement.ui.view.BottomBarView
import com.example.taskmanagement.ui.view.MainActivityScreen
import com.example.taskmanagement.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), ITaskListener {
	
	private val taskViewModel : TaskViewModel by viewModels()
	
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Scaffold(bottomBar = { BottomBarView(this@MainActivity) }) {
				Modifier.padding(it)
				MainActivityScreen(taskViewModel.pendingTaskCount, taskViewModel.completedTaskCount,
				                   taskViewModel.cancelledTaskCount, this@MainActivity,
				                   taskViewModel.taskList)
			}
		}
	}
	
	override fun onResume() {
		super.onResume()
		taskViewModel.getTaskList()
		taskViewModel.getFilteredList(0)
		taskViewModel.getFilteredList(1)
		taskViewModel.getFilteredList(2)
	}
	
	override fun updateTask(taskModel : TaskModel?) {
		val intent = Intent(this, AddTaskActivity::class.java)
		intent.putExtra("TaskModel", taskModel)
		startActivity(intent)
	}
	
	override fun deleteTask(taskID : Int) {
		confirmDeleteTask(taskID)
	}
	
	override fun openAddTaskScreen() {
		startActivity(Intent(this, AddTaskActivity::class.java))
	}
	
	private fun confirmDeleteTask(taskId : Int) {
		val alertDialog = AlertDialog.Builder(this)
				.setTitle("Confirmation")
				.setMessage("Are you sure to delete this task?")
				.setPositiveButton("Delete") { dialog, which ->
					taskViewModel.deleteTask(taskId)
					taskViewModel.getTaskList()
					dialog?.dismiss()
				}
				.setNegativeButton("Cancel") { dialog, which -> dialog?.dismiss() }
				.create()
		alertDialog.show()
	}
}