package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(app: Application) : AndroidViewModel(app) {
    val todo = MutableLiveData<Todo>()

    fun add(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoDatabase(getApplication()).todoDao().insertOrUpdate(todo)
        }
    }

    fun fetch(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            todo.postValue(
                TodoDatabase(getApplication()).todoDao().find(id)
            )
        }
    }

    fun update(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoDatabase(getApplication()).todoDao().insertOrUpdate(todo)
        }
    }
}