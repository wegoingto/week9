package com.example.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.Todo

class TodoListAdapter(val todos:ArrayList<Todo>,
                      val adapterOnClick:(Todo)-> Unit)
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){

    class TodoViewHolder(var v: View):RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout,parent,false)
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val checkTask = holder.v.findViewById<CheckBox>(R.id.checkTask)
        checkTask.text = todos[position].title
        checkTask.setOnCheckedChangeListener { compoundButton, b ->
            adapterOnClick(todos[position])
        }
    }

    fun updateTodoList(newTodos: List<Todo>){
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }
}