package com.example.taskmanagement.model

import android.os.Parcel
import android.os.Parcelable

data class TaskModel(val _id : Int = 0,
                     var title : String,
                     var description : String,
                     var status : Int = 0) : Parcelable {
	
	constructor(parcel : Parcel) : this(parcel.readInt(), parcel.readString() ?: "",
	                                    parcel.readString() ?: "", parcel.readInt()) {
	}
	
	override fun writeToParcel(parcel : Parcel, flags : Int) {
		parcel.writeInt(_id)
		parcel.writeString(title)
		parcel.writeString(description)
		parcel.writeInt(status)
	}
	
	override fun describeContents() : Int {
		return 0
	}
	
	companion object CREATOR : Parcelable.Creator<TaskModel> {
		
		override fun createFromParcel(parcel : Parcel) : TaskModel {
			return TaskModel(parcel)
		}
		
		override fun newArray(size : Int) : Array<TaskModel?> {
			return arrayOfNulls(size)
		}
	}
}
