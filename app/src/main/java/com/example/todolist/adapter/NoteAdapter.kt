package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todolist.data.Note
import com.example.todolist.databinding.ItemNoteBinding

class NoteAdapter(var items: List<Note>,
                  val onClick: (Int) -> Unit,
                  val onDelete: (Int) -> Unit
) : Adapter<NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = items[position]
        holder.render(note)
        holder.itemView.setOnClickListener {
            onClick(position)
        }
        holder.binding.deleteButton.setOnClickListener {
            onDelete(position)
        }
    }

    fun updateItems(items: List<Note>) {
        this.items = items
        notifyDataSetChanged()
    }
}


class NoteViewHolder(val binding: ItemNoteBinding) : ViewHolder(binding.root) {

    fun render(note: Note) {

        if (note.private) {
            binding.privateNoteSwitch.isChecked = true
            binding.titleTextView.text = "Private Note"
        } else {
            binding.privateNoteSwitch.isChecked = false
            binding.titleTextView.text = note.title
        }
    }
}

