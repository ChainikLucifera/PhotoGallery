package com.example.photogallery

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photogallery.databinding.ActivityMainBinding
import com.example.photogallery.models.PhotoResult

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ImageAdapter
    private val images = mutableListOf<PhotoResult>()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.RV.layoutManager = GridLayoutManager(this, 2)
        adapter = ImageAdapter(images)
        binding.RV.adapter = adapter

        binding.Btn.setOnClickListener {
            val query = binding.EditText.text.toString()
            if (query.isNotEmpty()) {
                searchImages(query)
            }
            else{
                Toast.makeText(this, "Enter search query", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchImages(query: String){
    }
}