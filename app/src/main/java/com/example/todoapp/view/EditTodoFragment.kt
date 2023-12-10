package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentEditTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.DetailTodoViewModel

class EditTodoFragment : Fragment() {

    private var _view: FragmentEditTodoBinding? = null
    private val view get() = _view!!

    private val todoViewModel: DetailTodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _view = FragmentEditTodoBinding.inflate(inflater, container, false)
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _view = null
    }

    private fun saveChanges() {
        todoViewModel.update(
            Todo(
                title = view.editTextTodoTitle.text.toString(),
                notes = view.editTextTodoNotes.text.toString(),
                priority = view.radioGroupPriority.findViewById<RadioButton>(
                    view.radioGroupPriority.checkedRadioButtonId
                ).tag.toString().toInt(),
            ).apply {
                uid= EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
            }
        )
        Toast.makeText(requireContext(), "Changes saved", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(view.root).popBackStack()
    }
}