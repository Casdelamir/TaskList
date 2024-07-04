package com.example.tasklist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tasklist.data.Task
import com.example.tasklist.data.TaskDAO
import com.example.tasklist.databinding.ActivityTaskDetailBinding
import com.example.tasklist.databinding.TaskRecicleViewLayoutBinding

class TaskDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskDetailBinding
    lateinit var taskDAO: TaskDAO
    lateinit var task: Task
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", -1)

        taskDAO = TaskDAO(this)

        if (id != -1) {
            task = taskDAO.find(id)!!
            binding.nameEditText.setText(task.name)
            binding.descriptionEditText.setText(task.description)
        }

        binding.saveButton.setOnClickListener() {
            val name = binding.nameEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            val newTask = Task(id, name, description)

            if (name != "") {
                if (id != -1) {
                    taskDAO.update(newTask)
                    Toast.makeText(this, "Task is updated", Toast.LENGTH_SHORT).show()
                }else {
                    taskDAO.insert(newTask)
                    Toast.makeText(this, "Task is saved", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Enter task name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}