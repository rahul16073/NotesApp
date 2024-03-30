package com.example.notesapp.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.domain.Note
import com.example.notesapp.presentation.ViewModel
import com.example.notesapp.presentation.adapter.NoteAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteFragment: Fragment(R.layout.fragment_notes), NoteAdapter.OnNoteClickListener {
    val viewModel by viewModels<ViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNotesBinding.bind(requireView())
        binding.apply {
            recycle.layoutManager = GridLayoutManager(context, 2)
            recycle.setHasFixedSize(true)
            addBtn.setOnClickListener {
                val action = NoteFragmentDirections.actionNoteFragmentToAddEditNoteFragment(
                    null)
                findNavController().navigate(action)
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.notes.collect {notes->
                    val adapter = NoteAdapter(notes, this@NoteFragment)
                    recycle.adapter = adapter
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.notesEvent.collect {event->
                   if(event is ViewModel.NotesEvent.ShowUndoSnackBar)
                       Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).
                               setAction("UNDO"){viewModel.insertNote(event.note)}.show()
                }
            }
        }
    }

    override fun onNoteClick(note: Note) {
        val action = NoteFragmentDirections.actionNoteFragmentToAddEditNoteFragment(note)
        findNavController().navigate(action)
    }

    override fun onNoteLongClick(note: Note) {
       viewModel.deleteNote(note)
    }
}