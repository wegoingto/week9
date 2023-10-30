package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @ColumnInfo(name="title")   //untuk perbedaan penamaan
    var title:String,
    @ColumnInfo(name="notes")
    var notes:String
    ){
    @PrimaryKey(autoGenerate = true)
    var uid:Int = 0
}
