package com.example.todolist.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist.R
import com.example.todolist.data.Note
import com.example.todolist.data.NoteDAO
import com.example.todolist.databinding.ActivityNoteBinding
import kotlin.system.measureTimeMillis

class NoteActivity : AppCompatActivity() {

    companion object {
        const val NOTE_ID = "NOTE_ID"
    }

    lateinit var binding: ActivityNoteBinding

    lateinit var noteDAO: NoteDAO

    lateinit var note: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setContentView(R.layout.activity_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getLongExtra(NOTE_ID, -1L)

        noteDAO = NoteDAO(this)

        if (id != -1L){
            note = noteDAO.findById(id)!!
            binding.noteTitleEditText.setText(note.title)
            binding.noteDescriptionEditText.setText(note.description)

            TODO()

        }else{
            note = Note(-1L,"","",)
        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()

            note.title = title

            if (note.id != -1L) {
                noteDAO.update(note)
            } else {
                noteDAO.insert(note)
            }

            finish()
        }
    }
}