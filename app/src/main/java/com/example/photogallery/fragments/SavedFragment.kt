package com.example.photogallery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photogallery.MainActivity
import com.example.photogallery.adapters.ImageAdapter
import com.example.photogallery.databinding.FragmentSavedBinding
import com.example.photogallery.models.Result
import com.example.photogallery.utils.FavouritesManager


class SavedFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var adapter: ImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        loadFavourites()
    }

    private fun setupRecyclerView() {
        binding.SavedImagesRV.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ImageAdapter(emptyList()) { image ->
            showImageDetails(image)
        }

        adapter.onFavouriteClick = { image, becameFavourite ->
            if(!becameFavourite){
                FavouritesManager.removeFromFavourites(image.id)
                Toast.makeText(
                    requireContext(),
                    "Removed from favourites",
                    Toast.LENGTH_SHORT
                ).show()
                loadFavourites()
            }
        }

        binding.SavedImagesRV.adapter = adapter
    }

    private fun showImageDetails(image: Result) {
        (requireActivity() as MainActivity).showImageDetails(image)
    }

    private fun loadFavourites() {

        val favourites = FavouritesManager.getFavourites()

        if (favourites.isEmpty()) {
            binding.EmptyStateText.visibility = View.VISIBLE
            binding.SavedImagesRV.visibility = View.GONE
        } else {
            binding.EmptyStateText.visibility = View.GONE
            binding.SavedImagesRV.visibility = View.VISIBLE

            adapter.updateImages(favourites)
        }
    }
}