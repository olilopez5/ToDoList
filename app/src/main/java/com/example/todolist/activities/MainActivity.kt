package com.example.todolist.activities

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.TaskAdapter
import com.example.todolist.data.Task
import com.example.todolist.data.TaskDAO
import com.example.todolist.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    lateinit var taskDAO: TaskDAO

    lateinit var taskList : List<Task>

    lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.dateTextView.text = getTodayDate()
        supportActionBar?.title = "Today´s reminders"

        //setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(com.example.todolist.R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        taskDAO = TaskDAO(this)

        adapter = TaskAdapter(
            emptyList(),
            { position ->
                val task = taskList[position]

                val intent = Intent(this, TaskActivity::class.java)
                intent.putExtra(TaskActivity.TASK_ID, task.id)
                startActivity(intent)
            },
            { position ->
                val task = taskList[position]

                AlertDialog.Builder(this)
                    .setTitle("Delete task")
                    .setMessage("Are you sure you want to delete this task?")
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        taskDAO.delete(task)
                        refreshData()
                    }
                    .setNegativeButton(android.R.string.cancel, null)
                    .setCancelable(false)
                    .show()

            },
            { position ->
                val task = taskList[position]

                task.done = !task.done
                taskDAO.update(task)
                refreshData()

            },
        )


        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    fun refreshData() {
        taskList = taskDAO.findAll()
        adapter.updateItems(taskList)
    }
    fun getTodayDate(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }
}

