package com.example.tasklist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tasklist.activity.TaskDetailsActivity
import com.example.tasklist.data.Task
import com.example.tasklist.data.TaskDAO
import com.example.tasklist.databinding.ActivityMainBinding
import com.example.tasklist.utils.TaskRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: TaskRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskDAO = TaskDAO(this)

        var task = Task(-1, "Comprar leche", false)
        taskDAO.insert(task)

        Log.i("DATABASE", task.toString())

        task.done = true

        taskDAO.update(task)

        Log.i("DATABASE", task.toString())

        task = taskDAO.find(task.id)!!

        Log.i("DATABASE", task.toString())

        //taskDAO.delete(task)

        val taskList = taskDAO.findAll()

        Log.i("DATABASE", taskList.toString())

        val adapter = TaskRecyclerViewAdapter(taskDAO.findAll(),
            { task ->
                navigateToTaskDetails(task)
            },
            { task ->
                if(task.done) {
                    task.done = false
                    taskDAO.update(task)
                }else {
                    task.done = true
                    taskDAO.update(task)
                }
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
}