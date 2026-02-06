package com.example.tasklist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tasklist.data.Category
import com.example.tasklist.data.CategoryDAO
import com.example.tasklist.databinding.ActivityCreateCategoryBinding

class CreateCategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateCategoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryDAO = CategoryDAO(this)

        binding.saveButton.setOnClickListener {
            val name = binding.categoryEditTextName.text.toString()
            val existing  = categoryDAO.findByName(name)
            if (existing == null) {
                categoryDAO.insert(Category(-1, name))
                Toast.makeText(this, "Category is created", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, "Category with the name $name exist !!",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }


}