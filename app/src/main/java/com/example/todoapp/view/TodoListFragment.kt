package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.viewmodel.DetailTodoViewModel
import com.example.todoapp.viewmodel.ListTodoViewModel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TodoListFragment : Fragment() {
    private val todoViewModel: ListTodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_todo)
        val textEmpty: TextView = view.findViewById(R.id.text_message_empty_todo)
        val buttonAdd: ExtendedFloatingActionButton = view.findViewById(R.id.button_add_todo)

        recyclerView.adapter = TodoAdapter(
            onItemCheck = {
                todoViewModel.setDone(it)
                (recyclerView.adapter as TodoAdapter).removeItem(it)
                textEmpty.visibility =
                    if ((recyclerView.adapter as TodoAdapter).itemCount > 0) View.VISIBLE
                    else View.GONE
            }
        )

        buttonAdd.setOnClickListener {
            Navigation.findNavController(view).navigate(
                TodoListFragmentDirections.actionCreateTodo()
            )
        }

        todoViewModel.todos.observe(viewLifecycleOwner) { todos ->
            (recyclerView.adapter as TodoAdapter).setData(todos)
            textEmpty.visibility = if (todos.isEmpty()) View.VISIBLE else View.GONE
        }

        todoViewModel.refresh()
    }
}