package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.viewmodel.DetailTodoViewModel
import com.example.todoapp.viewmodel.ListTodoViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class   TodoListFragment : Fragment() {
    private lateinit var viewModel: ListTodoViewModel
    private val adapter = TodoListAdapter(arrayListOf(),
        { viewModel.clearTask(it)})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)
        viewModel.refresh()
        val recView = view.findViewById<RecyclerView>(R.id.recView)
        recView.layoutManager = LinearLayoutManager(context)
        recView.adapter = adapter

        val fab = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        fab.setOnClickListener{
            val action = TodoListFragmentDirections.actionCreateTodo()
            Navigation.findNavController(it).navigate(action)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    fun observeViewModel(){
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            adapter.updateTodoList(it)
            val txtEmpty = view?.findViewById<TextView>(R.id.txtEmpty)
            if (it.isEmpty()) {
                txtEmpty?.visibility = View.VISIBLE
            } else {
                txtEmpty?.visibility = View.INVISIBLE
            }
        })
    }
}