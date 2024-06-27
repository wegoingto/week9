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
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoapp.R
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.DetailTodoViewModel
import java.util.concurrent.TimeUnit


class CreateTodoFragment : Fragment() {
    private val todoViewModel: DetailTodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextTitle: EditText = view.findViewById(R.id.edit_text_todo_title)
        val editTextNotes: EditText = view.findViewById(R.id.edit_text_todo_notes)
        val radioGroupPriority: RadioGroup = view.findViewById(R.id.radio_group_priority)
        val buttonAdd: Button = view.findViewById(R.id.button_add_todo)

        buttonAdd.setOnClickListener {
            todoViewModel.add(
                Todo(
                    title = editTextTitle.text.toString(),
                    notes = editTextNotes.text.toString(),
                    priority = radioGroupPriority.findViewById<RadioButton>(
                        radioGroupPriority.checkedRadioButtonId
                    ).tag.toString().toInt(),
                )
            )
            Toast.makeText(requireContext(), "Todo added", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }
    }
}