package com.example.tasklist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tasklist.R
import com.example.tasklist.data.Category
import com.example.tasklist.data.CategoryDAO
import com.example.tasklist.databinding.ActivityCreateCategoryBinding
import com.example.tasklist.databinding.ActivityTaskDetailBinding

class CreateCategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryDAO = CategoryDAO(this)

        binding.saveButton.setOnClickListener() {
            categoryDAO.insert(Category(-1, binding.categoryEditTextName.text.toString()))
        }
    }


}