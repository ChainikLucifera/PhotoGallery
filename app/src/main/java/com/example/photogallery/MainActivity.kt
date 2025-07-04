package com.example.photogallery

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photogallery.api.RetrofitClient
import com.example.photogallery.databinding.ActivityMainBinding
import com.example.photogallery.models.OpenVerseResponse
import com.example.photogallery.models.PhotoResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            } else {
                Toast.makeText(this, "Enter search query", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchImages(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiService.searchImages(query = query, page = 1)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val apiResponse: OpenVerseResponse = response.body() as OpenVerseResponse
                        apiResponse?.let {
                            it.results?.let { results ->
                                images.addAll(results)
                                adapter.notifyDataSetChanged()
                            }
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "${response.code()} ${response.message()}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main){
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            } finally {

            }
        }
    }
}