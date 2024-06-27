package com.example.todoapp.view

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CompoundButton.OnCheckedChangeListener
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.TodoItemLayoutBinding
import com.example.todoapp.model.Todo

class TodoAdapter(
    private val todos: MutableList<Todo> = mutableListOf(),
    private val onItemCheck: (Todo) -> Unit,
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            getTodo = { position -> todos[position] },
            onChecked = onItemCheck,
            onEditClicked = {
                parent.findNavController().navigate(TodoListFragmentDirections.actionEdit(it.id))
            }
        )
    }

    override fun getItemCount() = todos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.todo = todos[position]
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

    class ViewHolder(
        val binding: TodoItemLayoutBinding,
        getTodo: (position: Int) -> Todo,
        onChecked: (todo: Todo) -> Unit,
        onEditClicked: (todo: Todo) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.onCheckListener = OnCheckedChangeListener { _, isChecked ->
                if (isChecked) onChecked(getTodo(adapterPosition))
            }

            binding.onEditClickListener = OnClickListener {
                onEditClicked(getTodo(adapterPosition))
            }
        }
    }
}