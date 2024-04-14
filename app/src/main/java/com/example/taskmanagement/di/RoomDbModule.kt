package com.example.taskmanagement.di

import android.content.Context
import androidx.room.Room
import com.example.taskmanagement.db.TaskEntityDao
import com.example.taskmanagement.db.TaskEntityDaoImpl
import com.example.taskmanagement.db.TaskEntityDataSource
import com.example.taskmanagement.db.TaskManagementDb
import com.example.taskmanagement.mapper.BaseModelMapper
import com.example.taskmanagement.mapper.ModelMapper
import com.example.taskmanagement.model.TaskEntity
import com.example.taskmanagement.model.TaskModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDbModule {
	
	@Singleton
	@Provides
	fun provideTaskManagementDb(@ApplicationContext
	                            context : Context) : TaskManagementDb = Room.databaseBuilder(
		context, TaskManagementDb::class.java, "task_manager").allowMainThreadQueries().build()
	
	@Singleton
	@Provides
	fun getTaskDbDao(db : TaskManagementDb) : TaskEntityDao = db.taskEntityDao()
	
	
	@Singleton
	@Provides
	fun provideTaskItemMapper() : BaseModelMapper<TaskEntity, TaskModel> = ModelMapper()
	
	
	@Singleton
	@Provides
	fun getTaskDaoImpl(dao : TaskEntityDao, mapper : ModelMapper) : TaskEntityDataSource {
		return TaskEntityDaoImpl(dao, mapper)
	}
}