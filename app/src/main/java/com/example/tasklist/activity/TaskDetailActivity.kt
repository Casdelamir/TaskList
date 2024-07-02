package com.example.tasklist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasklist.databinding.ActivityTaskDetailBinding
import com.example.tasklist.databinding.TaskRecicleViewLayoutBinding

class TaskDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTaskDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}