package com.example.photogallery

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.api.RetrofitClient
import com.example.photogallery.databinding.ActivityMainBinding
import com.example.photogallery.models.OpenVerseResponse
import com.example.photogallery.models.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ImageAdapter
    private val images = mutableListOf<Result>()
    lateinit var binding: ActivityMainBinding

    private var currentPage = 1
    private var totalPages = 1

    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        binding.RV.layoutManager = GridLayoutManager(this, 2)
        adapter = ImageAdapter(images) { image ->
            showImageDetails(image)
        }
        binding.RV.adapter = adapter

        binding.RV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (currentPage < totalPages && !isLoading) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0
                    ) {
                        loadMoreData()
                    }
                }
            }
        })

        binding.Btn.setOnClickListener {
            val query = binding.EditText.text.toString()
            if (query.isNotEmpty()) {
                currentPage = 1
                searchImages(query)
            } else {
                Toast.makeText(this, "Enter search query", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadMoreData() {
        if (isLoading) {
            return
        }

        currentPage++
        val query = binding.EditText.text.toString()
        if (query.isNotEmpty()) {
            searchImages(query)
        }
    }

    private fun showLoading(show: Boolean) {
        binding.PB.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun searchImages(query: String) {
        if (isLoading) {
            return
        }
        isLoading = true
        showLoading(true)

        CoroutineScope(Dispatchers.IO).launch {
            try {

                val response =
                    RetrofitClient.apiService.searchImages(query = query, page = currentPage)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val apiResponse: OpenVerseResponse = response.body() as OpenVerseResponse
                        apiResponse?.let {
                            totalPages = it.page_count

                            if (currentPage == 1) {
                                images.clear()
                            }

                            it.results?.let { results ->
                                images.addAll(results)
                                adapter.notifyDataSetChanged()
                            }
                        }

                        Toast.makeText(this@MainActivity, currentPage, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            this@MainActivity, "${response.code()} ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            } finally {
                withContext(Dispatchers.Main) {
                    isLoading = false
                    showLoading(false)
                }
            }
        }
    }

    private fun showImageDetails(image: Result){
        binding.fragmentContainer.visibility = View.VISIBLE
        val fragment = ImageDetailsFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}