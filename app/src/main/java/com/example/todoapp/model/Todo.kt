package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @ColumnInfo(name="title")   //untuk perbedaan penamaan
    var title:String,
    @ColumnInfo(name="notes")
    var notes:String,
    @ColumnInfo(name = "priority")
    var priority: Int = 2,
    @ColumnInfo(name = "is_done")
    var isDone: Boolean = false,
    ){
    @PrimaryKey(autoGenerate = true)
    var uid:Int = 0
}
