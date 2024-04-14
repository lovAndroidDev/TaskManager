package com.example.taskmanagement.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.taskmanagement.R
import com.example.taskmanagement.interfaces.ITaskListener
import com.example.taskmanagement.model.TaskModel
import com.example.taskmanagement.ui.resource.CustomColors
import com.example.taskmanagement.ui.resource.CustomShapeCompose
import com.example.taskmanagement.ui.resource.CustomTextStyle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainActivityScreen(pendingTask : StateFlow<Int>?,
                       completedTask : StateFlow<Int>?,
                       cancelledTask : StateFlow<Int>?,
                       clickListener : ITaskListener,
                       _taskList : StateFlow<List<TaskModel>>?) {
	val taskList = _taskList?.collectAsStateWithLifecycle()?.value
	val pendingTaskCount = pendingTask?.collectAsStateWithLifecycle()?.value
	val completedTaskCount = completedTask?.collectAsStateWithLifecycle()?.value
	val cancelledTaskCount = cancelledTask?.collectAsStateWithLifecycle()?.value
	Column(modifier = Modifier.fillMaxSize()) {
		if (taskList.isNullOrEmpty()) {
			Text(text = "No Task Found", style = CustomTextStyle.text14spBlackBold,
			     modifier = Modifier.padding(horizontal = 50.dp, vertical = 100.dp))
		} else {
			TopHeaderView(pendingTask = pendingTaskCount ?: 0,
			              completedTask = completedTaskCount ?: 0,
			              cancelledTask = cancelledTaskCount ?: 0)
			TaskListView(taskModelList = taskList, clickListener = clickListener)
		}
	}
}

@Composable
fun BottomBarView(clickListener : ITaskListener) {
	BottomAppBar(tonalElevation = 8.dp) {
		Box(contentAlignment = Alignment.Center, modifier = Modifier
				.fillMaxWidth()
				.height(40.dp)) {
			Row(modifier = Modifier.background(CustomColors.green1,
			                                   shape = CustomShapeCompose.roundedCorner8dp)) {
				Text(text = "Add New Task", style = CustomTextStyle.text12spWhite,
				     modifier = Modifier
						     .wrapContentWidth()
						     .padding(horizontal = 10.dp, vertical = 8.dp)
						     .clickable {
							     clickListener.openAddTaskScreen()
						     })
			}
		}
	}
}

@Composable
private fun TopHeaderView(pendingTask : Int, completedTask : Int, cancelledTask : Int) {
	Row(horizontalArrangement = Arrangement.SpaceEvenly,
	    modifier = Modifier
			    .background(CustomColors.Purple80)
			    .fillMaxWidth()) {
		Column(horizontalAlignment = Alignment.CenterHorizontally,
		       modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
			Image(painter = painterResource(id = R.drawable.ic_pending_task),
			      contentDescription = "", modifier = Modifier.size(30.dp, 30.dp))
			Text(text = "$pendingTask", style = CustomTextStyle.text12spWhiteBold,
			     modifier = Modifier.padding(top = 10.dp))
		}
		Column(horizontalAlignment = Alignment.CenterHorizontally,
		       modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
			Image(painter = painterResource(id = R.drawable.ic_completed_task),
			      contentDescription = "", modifier = Modifier.size(30.dp, 30.dp))
			Text(text = "$completedTask", style = CustomTextStyle.text12spWhiteBold,
			     modifier = Modifier.padding(top = 10.dp))
		}
		Column(horizontalAlignment = Alignment.CenterHorizontally,
		       modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp)) {
			Image(painter = painterResource(id = R.drawable.ic_cancelled_task),
			      contentDescription = "", modifier = Modifier.size(30.dp, 30.dp))
			Text(text = "$cancelledTask", style = CustomTextStyle.text12spWhiteBold,
			     modifier = Modifier.padding(top = 10.dp))
		}
	}
}

@Composable
fun TaskListView(taskModelList : List<TaskModel>?, clickListener : ITaskListener) {
	LazyColumn(modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)) {
		taskModelList?.let {
			for (item in it) {
				item {
					Column(modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)) {
						TaskListItemViw(item, clickListener)
					}
				}
			}
		}
	}
}

@Composable
fun TaskListItemViw(taskModel : TaskModel? = null, clickListener : ITaskListener) {
	Row(Modifier
			    .fillMaxWidth()
			    .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))) {
		Row(verticalAlignment = Alignment.CenterVertically,
		    modifier = Modifier.padding(vertical = 8.dp)) {
			Row(modifier = Modifier
					.padding(horizontal = 10.dp)
					.weight(0.3F, true)) {
				var img = painterResource(R.drawable.ic_pending_task)
				if (taskModel?.status == 1) {
					img = painterResource(R.drawable.ic_completed_task)
				} else if (taskModel?.status == 2) {
					img = painterResource(R.drawable.ic_cancelled_task)
				}
				Image(painter = img, contentDescription = "",
				      modifier = Modifier.size(width = 50.dp, 50.dp))
			}
			Row(modifier = Modifier.weight(1F, true)) {
				Column {
					Text(text = taskModel?.title ?: "", style = CustomTextStyle.text12spBlack)
					Text(text = taskModel?.description ?: "", style = CustomTextStyle.text14spBlack,
					     modifier = Modifier.padding(vertical = 5.dp))
					var statusText = "Pending"
					var modifier = Modifier
							.background(color = CustomColors.orange1,
							            shape = RoundedCornerShape(6.dp))
							.padding(horizontal = 10.dp, vertical = 2.dp)
					
					if (taskModel?.status == 1) {
						statusText = "Completed"
						modifier = Modifier
								.background(color = CustomColors.green1,
								            shape = RoundedCornerShape(6.dp))
								.padding(horizontal = 10.dp, vertical = 2.dp)
					} else if (taskModel?.status == 2) {
						statusText = "Cancelled"
						modifier = Modifier
								.background(color = CustomColors.red5,
								            shape = RoundedCornerShape(6.dp))
								.padding(horizontal = 10.dp, vertical = 2.dp)
					}
					
					Text(text = statusText, style = CustomTextStyle.text12spWhite,
					     modifier = modifier)
				}
			}
			Row(horizontalArrangement = Arrangement.End,
			    modifier = Modifier
					    .padding(vertical = 10.dp)
					    .weight(0.3F, true)) {
				val editImage = painterResource(id = R.drawable.ic_edit)
				val deleteImage = painterResource(id = R.drawable.ic_delete)
				Image(painter = editImage, contentDescription = "", modifier = Modifier.clickable {
					clickListener.updateTask(taskModel)
				})
				Image(painter = deleteImage, contentDescription = "",
				      modifier = Modifier
						      .padding(horizontal = 15.dp)
						      .clickable {
							      clickListener.deleteTask(taskModel?._id ?: 0)
						      })
			}
		}
	}
}