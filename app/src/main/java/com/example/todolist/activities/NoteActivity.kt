package com.example.todolist.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.adapter.NoteAdapter
import com.example.todolist.data.Note
import com.example.todolist.data.NoteDAO
import com.example.todolist.databinding.ActivityNoteBinding
import kotlin.system.measureTimeMillis

class NoteActivity : AppCompatActivity() {

    companion object {
        const val NOTE_ID = "NOTE_ID"
    }

    lateinit var binding: ActivityNoteBinding

    lateinit var adapter: NoteAdapter

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



//        adapter = NoteAdapter(
//            emptyList(),
//            { position ->
//                val note = noteList[position]
//
//                val intent = Intent(this, TaskActivity::class.java)
//                intent.putExtra(TaskActivity.NOTE_ID, note.id)
//                startActivity(intent)
//            },
//            { position ->
//                val note = noteList[position]
//
//                AlertDialog.Builder(this)
//                    .setTitle("Delete note")
//                    .setMessage("Are you sure you want to delete this note?")
//                    .setPositiveButton(android.R.string.ok) { _, _ ->
//                        noteDAO.delete(note)
//                        refreshData()
//                    }
//                    .setNegativeButton(android.R.string.cancel, null)
//                    .setCancelable(false)
//                    .show()
//
//            },
//            { position ->
//                val note = noteList[position]
//
//                note.private = !note.private
//                NoteDAO.update(note)
//                refreshData()
//
//            },
//        )
//
//
//        binding.recyclerView.adapter = adapter
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//
//        binding.addNoteButton.setOnClickListener {
//            val intent = Intent(this, NoteActivity::class.java)
//            startActivity(intent)
//        }
//
//    }


        if (id != -1L){
            note = noteDAO.findById(id)!!
            binding.noteTitleEditText.setText(note.title)
            binding.noteDescriptionEditText.setText(note.description)

            TODO()

        }else{
            note = Note(-1L,"","",-1L)
        }



// Hacer que el campo de contraseña sea visible solo si la nota es privada

        binding.privateNoteSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.passwordInputLayout.visibility = View.VISIBLE
            } else {
                binding.passwordInputLayout.visibility = View.GONE
            }
        }

        // Guardar la nota
        binding.saveNoteButton.setOnClickListener {
            val title = binding.noteTitleEditText.text.toString()
            val description = binding.noteDescriptionEditText.text.toString()
            val isPrivate = binding.privateNoteSwitch.isChecked
            var password: String? = null

            // Si la nota es privada, obtener la contraseña del campo correspondiente
            if (isPrivate) {
                password = binding.passwordEditText.text.toString()
            }

            // Establecer los valores de la nota
            note.title = title
            note.description = description


            if (isPrivate) {
                note.password =
            }


            if (note.id != -1L) {
                noteDAO.update(note)
            } else {
                noteDAO.insert(note)
            }

            finish()
        }

    }
}