package com.example.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.TodoItemLayoutBinding
import android.widget.CompoundButton.OnCheckedChangeListener
import android.widget.TextView.OnEditorActionListener
import com.example.todoapp.model.Todo

class TodoListAdapter(val todos:ArrayList<Todo>,
                      val adapterOnClick:(Todo)-> Unit)
    :RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(){

    class TodoViewHolder(
        val view:TodoItemLayoutBinding,
        getTodo: (position: Int) -> Todo,
        onChecked: (todo: Todo) -> Unit,
        onEditClicked: (todo: Todo) -> Unit,
    ) : RecyclerView.ViewHolder(view.root) {
        init {
            view.listener= OnCheckedChangeListener { _, isChecked ->
                if (isChecked) onChecked(getTodo(adapterPosition))
            }

            view.onEditClickListener = OnClickListener {
                onEditClicked(getTodo(adapterPosition))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoItemLayoutBinding.inflate((LayoutInflater.from(parent.context)), parent, false),
            getTodo = { position -> todos[position] },
            onChecked = adapterOnClick,
            onEditClicked = { parent.findNavController().navigate(TodoListFragmentDirections.actionEdit(it.uid))
            }
        )
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.view.todo = todos[position]
    }

    fun updateTodoList(newTodos: List<Todo>){
        todos.clear()
        todos.addAll(newTodos)
        notifyDataSetChanged()
    }

    fun setData(data: List<Todo>) {
        todos.clear()
        todos.addAll(data)
        notifyDataSetChanged()
    }

    fun removeItem(todo: Todo) {
        val index = todos.indexOf(todo)
        todos.removeAt(index)
        notifyItemRemoved(index)
    }
}