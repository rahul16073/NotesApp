package com.example.notesapp.presentation.adapter

import android.R.attr.spacing
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.ItemNotesBinding
import com.example.notesapp.domain.Note


class NoteAdapter(private val mNotes: List<Note>, private val listener: OnNoteClickListener): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        val layoutParams: ViewGroup.LayoutParams = holder.itemView.getLayoutParams()
        if (layoutParams is MarginLayoutParams) {
            layoutParams.setMargins(30, 30, 30, 30)
        }
        holder.itemView.layoutParams = layoutParams
        return holder
    }

    override fun getItemCount(): Int {
        return mNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(mNotes[position]){
            holder.bind(this)
        }
    }
    inner class ViewHolder(private val binding: ItemNotesBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.apply {
                root.setOnClickListener{
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION)
                        listener.onNoteClick(mNotes[position])
                }
                root.setOnLongClickListener{
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION)
                        listener.onNoteLongClick(mNotes[position])
                    true
                }
            }
        }
         fun bind(note: Note){
             binding.apply {
                 titleNote.text = note.title
                 contentNote.text = note.content
                 val formatter  = SimpleDateFormat("dd/mm/yyyy")
                 dateNote.text = formatter.format(note.date)
             }
         }
    }

    interface OnNoteClickListener{
        fun onNoteClick(note: Note)
        fun onNoteLongClick(note: Note)
    }
}