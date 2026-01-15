package com.example.photogallery.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photogallery.MainActivity
import com.example.photogallery.adapters.ImageAdapter
import com.example.photogallery.databinding.FragmentSavedBinding
import com.example.photogallery.models.Result


class SavedFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var adapter: ImageAdapter
    private val savedImages = mutableListOf<Result>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedImages.isEmpty()) {
            savedImages.addAll(createTestImages())
        }
        setupRecyclerView()
        //updateEmptyState()

    }

    private fun createTestImages(): List<Result> {
        return listOf(
            Result(
                id = "1",
                title = "Test Image 1",
                url = "https://karton33.ru/images/kristall/korob.png",
                thumbnail = "null",
                creator = "Test Creator",
                creator_url = "null",
                license = "CC0",
                license_version = "1.0",
                license_url = "null",
                provider = "Test",
                source = "Test Source",
                tags = listOf(),
                attribution = "null",
                height = 500,
                width = 500,
                category = "null",
                detail_url = "null",
                fields_matched = listOf(),
                filesize = 0,
                filetype = "null",
                foreign_landing_url = "null",
                indexed_on = "null",
                mature = true,
                related_url = "null",
                unstable__sensitivity = listOf()
            )
        )
    }

    private fun setupRecyclerView() {
        binding.SavedImagesRV.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = ImageAdapter(savedImages) { image ->
            showImageDetails(image)
        }
        binding.SavedImagesRV.adapter = adapter
    }

    private fun showImageDetails(image: Result){
        (requireActivity() as MainActivity).showImageDetails(image)
    }
}