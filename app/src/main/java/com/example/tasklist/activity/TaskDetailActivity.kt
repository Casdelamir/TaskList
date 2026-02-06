package com.example.tasklist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.tasklist.data.CategoryDAO
import com.example.tasklist.data.Task
import com.example.tasklist.data.TaskDAO
import com.example.tasklist.databinding.ActivityTaskDetailBinding

class TaskDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskDetailBinding
    lateinit var taskDAO: TaskDAO
    lateinit var categoryDAO: CategoryDAO
    lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", -1)

        taskDAO = TaskDAO(this)
        categoryDAO = CategoryDAO(this)

        lateinit var selectedCategory: String

        //Get all categories for the spinner and load them to it
        val listCat = categoryDAO.findAll().map{ it.name }

        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, listCat)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spinner = binding.mySpinner
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                selectedCategory = selectedItem
                Toast.makeText(this@TaskDetailsActivity, "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        if (id != -1) {
            task = taskDAO.find(id)!!
            binding.nameEditText.setText(task.name)
            binding.descriptionEditText.setText(task.description)
//            binding.mySpinner.setText(task.category?.name)
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val description = binding.descriptionEditText.text.toString()

            val category = categoryDAO.findByName(selectedCategory)
            val newTask = Task(id, name, description, category)

            if (category == null) {
                Toast.makeText(this, "Invalid category", Toast.LENGTH_SHORT).show()
            } else {
                if (name != "") {
                    if (id != -1) {
                        taskDAO.update(newTask)
                        Toast.makeText(this, "Task is updated", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        taskDAO.insert(newTask)
                        Toast.makeText(this, "Task is saved", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this, "Enter task name", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}