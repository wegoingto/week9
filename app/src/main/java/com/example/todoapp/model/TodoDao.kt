package com.example.todoapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg todo: Todo)

    @Query("SELECT * FROM todo")
    fun selectAll() : List<Todo >

    @Query("SELECT * FROM todo WHERE uid= :id")
    fun selectTodo(id:Int): Todo

    @Query("SELECT * FROM todo WHERE is_done = 0 ORDER BY priority DESC")
    fun all(): List<Todo>

    @Query("SELECT * FROM todo WHERE uid = :id")
    fun find(id: Int): Todo

    @Delete
    fun deleteTodo(todo: Todo)

}