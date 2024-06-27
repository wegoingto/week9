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
    private var _binding: FragmentEditTodoBinding? = null
    private val binding get() = _binding!!

    private val todoViewModel: DetailTodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        todoViewModel.fetch(
            EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.onSaveClickListener = View.OnClickListener{ saveChanges() }

        todoViewModel.todo.observe(viewLifecycleOwner) {
            binding.todo = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveChanges() {
        todoViewModel.update(
            Todo(
                title = binding.editTextTodoTitle.text.toString(),
                notes = binding.editTextTodoNotes.text.toString(),
                priority = binding.radioGroupPriority.findViewById<RadioButton>(
                    binding.radioGroupPriority.checkedRadioButtonId
                ).tag.toString().toInt(),
            ).apply {
                id = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
            }
        )
        Toast.makeText(requireContext(), "Changes saved", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(binding.root).popBackStack()
    }
}