package com.example.todoapp.view

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoapp.R
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoWorker
import com.example.todoapp.util.NotificationHelper
import com.example.todoapp.viewmodel.DetailTodoViewModel
import java.util.concurrent.TimeUnit


class CreateTodoFragment : Fragment() {
    private lateinit var viewModel:DetailTodoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        val txtTitle = view.findViewById<EditText>(R.id.txtTitle)
        val txtNotes = view.findViewById<EditText>(R.id.txtNote)
        val rdGroup = view.findViewById<RadioGroup>(R.id.radioGroupPrio)
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)

        if(ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                NotificationHelper.REQUEST_NOTIF
            )
        }
        else{
            val workRequest = OneTimeWorkRequestBuilder<TodoWorker>()
                .setInitialDelay(30,TimeUnit.SECONDS)
                .setInputData(
                    workDataOf("title" to "Todo Created",
                    "message" to "A new todo has been created")
                )
                .build()
            WorkManager.getInstance(requireContext()).enqueue(workRequest)
        }

        btnAdd.setOnClickListener{
            val todo = Todo(txtTitle.text.toString(), txtNotes.text.toString(),
                rdGroup.findViewById<RadioButton>(rdGroup.checkedRadioButtonId).tag.toString().toInt())
            viewModel.addTodo(todo)
            Toast.makeText(it.context, "Todo Created", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NotificationHelper.REQUEST_NOTIF) {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            ) {
                val notif = NotificationHelper(view!!.context).createNotification(
                    "Todo Created",
                    "A new todo has been created! Stay focus!"
                )
            }
        }
    }
}