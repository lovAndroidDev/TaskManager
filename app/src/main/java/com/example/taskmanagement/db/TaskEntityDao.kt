package com.example.taskmanagement.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.taskmanagement.model.TaskEntity

@Dao
interface TaskEntityDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTaskInfo(taskEntity : TaskEntity)
	
	@Query("Select * From task_entity")
	suspend fun getTaskList() : List<TaskEntity>?
	
	@Query("Select * From task_entity where status =:status")
	suspend fun getFilteredTaskList(status : Int) : List<TaskEntity>?
	
	@Query("Delete From task_entity where id=:id")
	suspend fun deleteTask(id : Int)
	
	@Query(
		"Update task_entity set status=:taskStatus ,description = :taskDesc, title= :taskTitle" + " where id=:id")
	suspend fun updateTaskInfo(id : Int, taskTitle : String, taskDesc : String, taskStatus : Int)
}