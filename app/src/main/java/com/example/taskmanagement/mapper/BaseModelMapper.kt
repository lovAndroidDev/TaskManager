package com.example.taskmanagement.mapper

interface BaseModelMapper<Entity, T> {
	fun convertEntityToModel(input : Entity): T
	fun convertModelToEntity(input : T): Entity
	fun convertEntityToModelList(input : List<Entity>): List<T>
	fun convertModelToEntityList(input : List<T>): List<Entity>
}