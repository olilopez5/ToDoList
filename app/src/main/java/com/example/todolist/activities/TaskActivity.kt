package com.example.todolist.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todolist.R
import com.example.todolist.data.Task
import com.example.todolist.data.TaskDAO
import com.example.todolist.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity() {

    companion object {
        const val TASK_ID = "TASK_ID"
    }

    lateinit var binding: ActivityTaskBinding

    lateinit var taskDAO: TaskDAO

    var task = Task



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_task)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getLongExtra(TASK_ID, -1L)

        taskDAO = TaskDAO(this)

//        if (id!= 1L){
//            task = TaskDAO.findById(id)!!
//        }else{
//            task = Task(-1L,"")
//        }

        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()

            val task = Task(-1L, title)

            taskDAO.insert(task)

            finish()
        }
    }
}