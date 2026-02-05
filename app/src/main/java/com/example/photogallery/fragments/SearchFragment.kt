package com.example.photogallery.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photogallery.MainActivity
import com.example.photogallery.adapters.ImageAdapter
import com.example.photogallery.api.RetrofitClient
import com.example.photogallery.databinding.FragmentSearchBinding
import com.example.photogallery.utils.extensions.hideKeyboard
import com.example.photogallery.models.OpenVerseResponse
import com.example.photogallery.models.Result
import com.example.photogallery.utils.FavouritesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchFragment : Fragment() {

    private lateinit var adapter: ImageAdapter
    private val images = mutableListOf<Result>()
    lateinit var binding: FragmentSearchBinding

    private var currentPage = 1
    private var totalPages = 1

    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.RV.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ImageAdapter(images) { image ->
            showImageDetails(image)
        }

        adapter.onFavouriteClick = { image, isFavourite ->
            if (isFavourite) {
                Log.d("TEST", "isFavourite + $image")
                FavouritesManager.addToFavourites(image)
                Toast.makeText(
                    requireContext(),
                    "Added to favourites",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                FavouritesManager.removeFromFavourites(image.id)
                Toast.makeText(
                    requireContext(),
                    "Removed from favourites",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.RV.adapter = adapter

        binding.RV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 100) hideKeyboard()

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
                hideKeyboard()
                currentPage = 1
                searchImages(query)
            } else {
                Toast.makeText(requireContext(), "Enter search query", Toast.LENGTH_SHORT).show()
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

                        Toast.makeText(requireContext(), currentPage, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(
                            requireContext(), "${response.code()} ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                }
            } finally {
                withContext(Dispatchers.Main) {
                    isLoading = false
                    showLoading(false)
                }
            }
        }
    }

    private fun showImageDetails(image: Result) {
        (requireActivity() as MainActivity).showImageDetails(image)
    }

    companion object {

        @JvmStatic
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }
}