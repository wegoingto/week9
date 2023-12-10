package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoDatabase
import com.example.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application) :AndroidViewModel(application){
    val todo = MutableLiveData<Todo>()

    fun addTodo(todo: Todo){
        viewModelScope.launch(Dispatchers.IO){
            val db = buildDb(getApplication())
            db.todoDao().insert()
        }
    }
    fun fetch(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = buildDb(getApplication())
            db.todoDao().find(id)
        }
    }

    fun update(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = buildDb(getApplication())
            db.todoDao().insert()
        }
    }

}