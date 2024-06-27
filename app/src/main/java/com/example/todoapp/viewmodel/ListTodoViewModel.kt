package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListTodoViewModel(app: Application): AndroidViewModel(app) {
    val todos = MutableLiveData<List<Todo>>()
    val loading = MutableLiveData<Boolean>()
    val loadError = MutableLiveData<Boolean>()

    fun refresh() {
        loading.value = true
        loadError.value = false
        viewModelScope.launch(Dispatchers.IO) {
            todos.postValue(
                TodoDatabase(getApplication()).todoDao().all()
            )
            loading.postValue(false)
        }
    }

    fun setDone(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            TodoDatabase(getApplication()).todoDao().setDone(todo.id)
        }
    }
}