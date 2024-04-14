package com.example.taskmanagement.ui.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.taskmanagement.R
import com.example.taskmanagement.interfaces.ITaskAddListener
import com.example.taskmanagement.model.TaskModel
import com.example.taskmanagement.ui.resource.CustomColors
import com.example.taskmanagement.ui.resource.CustomShapeCompose
import com.example.taskmanagement.ui.resource.CustomTextStyle

private var finalTaskModel : TaskModel? = null

@Composable
fun AddTaskScreenView(taskModel : TaskModel?) {
	LazyColumn(modifier = Modifier.padding(horizontal = 20.dp, vertical = 70.dp)) {
		item {
			FormTextView(taskModel)
		}
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView(clickListener : ITaskAddListener) {
	TopAppBar(title = {
		Row(verticalAlignment = Alignment.CenterVertically,
		    modifier = Modifier
				    .fillMaxWidth()
				    .height(50.dp)) {
			Row(modifier = Modifier.padding(horizontal = 10.dp)) {
				Image(painter = painterResource(id = R.drawable.ic_back), contentDescription = "",
				      modifier = Modifier
						      .size(25.dp, 25.dp)
						      .clickable {
							      clickListener.close()
						      })
			}
			Text(text = "Add New Task", style = CustomTextStyle.text14spBlackBold)
		}
	}, modifier = Modifier.shadow(
		elevation = 5.dp,
		spotColor = Color.DarkGray,
	))
}

@Composable

private fun FormTextView(taskModel : TaskModel?) {
	var txtTitle by remember { mutableStateOf(TextFieldValue(taskModel?.title ?: "")) }
	var txtDescription by remember { mutableStateOf(TextFieldValue(taskModel?.description ?: "")) }
	
	finalTaskModel = taskModel
	if (taskModel == null) {
		finalTaskModel = TaskModel(title = "", description = "", status = 0)
	}
	val radioOptions = listOf("Pending", "Completed", "Cancelled")
	val (selectedOption, onOptionSelected) = remember {
		mutableStateOf(radioOptions[taskModel?.status ?: 0])
	}
	
	Column {
		OutlinedTextField(value = txtTitle, label = { Text(text = "Task Title") }, onValueChange = {
			taskModel?.title = it.text
			txtTitle = it
		}, modifier = Modifier.fillMaxWidth())
		
		OutlinedTextField(value = txtDescription, label = {
			Text(text = "Task " + "Description")
		}, onValueChange = {
			taskModel?.description = it.text
			txtDescription = it
		}, modifier = Modifier
				.fillMaxWidth()
				.padding(top = 20.dp))
		taskModel?.let {
			if (it._id != 0) {
				Text(text = "Task Status", style = CustomTextStyle.text14spBlackBold,
				     modifier = Modifier.padding(top = 20.dp))
				radioOptions.forEach { text ->
					Row(verticalAlignment = Alignment.CenterVertically,
					    modifier = Modifier.fillMaxWidth()) {
						RadioButton(selected = (text == selectedOption), onClick = {
							when (text) {
								"Pending"   -> {
									taskModel.status = 0
								}
								
								"Completed" -> {
									taskModel.status = 1
								}
								
								"Cancelled" -> {
									taskModel.status = 2
								}
							}
							onOptionSelected(text)
						})
						Text(text = text, modifier = Modifier.padding(start = 16.dp))
					}
				}
			}
		}
	}
}

@Composable
fun BottomBarView(clickListener : ITaskAddListener, taskID : Int) {
	val context = LocalContext.current
	BottomAppBar(tonalElevation = 8.dp) {
		var buttonTitle = "Add New Task"
		if (taskID != 0) {
			buttonTitle = "Update Task"
		}
		Box(contentAlignment = Alignment.Center, modifier = Modifier
				.fillMaxWidth()
				.height(40.dp)) {
			Row(modifier = Modifier.background(CustomColors.green1,
			                                   shape = CustomShapeCompose.roundedCorner8dp)) {
				Text(text = buttonTitle, style = CustomTextStyle.text12spWhiteBold,
				     modifier = Modifier
						     .wrapContentWidth()
						     .padding(horizontal = 10.dp, vertical = 8.dp)
						     .clickable {
							     if (validateform(context)) {
								     clickListener.addTaskClicked(finalTaskModel)
							     }
						     })
			}
		}
	}
}

private fun validateform(context : Context) : Boolean {
	finalTaskModel?.let {
		return if (it.title.isEmpty()) {
			Toast.makeText(context, "Enter Task Title...", Toast.LENGTH_SHORT).show()
			false
		} else if (it.description.isEmpty()) {
			Toast.makeText(context, "Enter Task Description...", Toast.LENGTH_SHORT).show()
			false
		} else {
			true
		}
	}
	return false
}

