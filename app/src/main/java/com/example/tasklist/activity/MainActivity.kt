package com.example.tasklist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tasklist.data.Category
import com.example.tasklist.data.CategoryDAO
import com.example.tasklist.data.Task
import com.example.tasklist.data.TaskDAO
import com.example.tasklist.databinding.ActivityMainBinding
import com.example.tasklist.utils.TaskRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: TaskRecyclerViewAdapter
    lateinit var taskList: List<Task>
    lateinit var taskDAO: TaskDAO
    lateinit var categoryDAO: CategoryDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskDAO = TaskDAO(this)
        categoryDAO = CategoryDAO(this)

        binding.buttonCreate.setOnClickListener() {
            val intent = Intent(this, TaskDetailsActivity::class.java)
            startActivity(intent)
        }

        binding.buttonCreateCategory.setOnClickListener() {
            val intent = Intent(this, CreateCategoryActivity::class.java)
            startActivity(intent)
        }

        adapter = TaskRecyclerViewAdapter(taskDAO.findAll(),
            { task ->
                navigateToTaskDetails(task)
            },
            { task ->
                if(task.done) {
                    task.done = false
                    taskDAO.update(task)
                    loadData()
                }else {
                    task.done = true
                    taskDAO.update(task)
                    loadData()
                }
            },
            {
                task -> taskDAO.delete(task)
                loadData()
            }
        )

        binding.recycle.adapter = adapter
        binding.recycle.layoutManager = GridLayoutManager(this, 1)
    }

    private fun navigateToTaskDetails(task: Task) {
        val intent = Intent(this, TaskDetailsActivity::class.java)
        intent.putExtra("id", task.id)
        startActivity(intent)
    }

    private fun loadData() {
        taskList = taskDAO.findAll()

        adapter.updateData(taskList)
    }

    override fun onResume() {
        super.onResume()

        loadData()
    }

}