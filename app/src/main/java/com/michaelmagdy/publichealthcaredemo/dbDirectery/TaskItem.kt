package com.michaelmagdy.publicHealthCareDemo.dbDirectery

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class TaskItem(

    @ColumnInfo(name = "title")
    var taskname: String,
    @ColumnInfo(name = "Description")
    var TaskDescription: String,
    @ColumnInfo(name = "priority")
    var priority: Int

):Parcelable  {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int=0
}
