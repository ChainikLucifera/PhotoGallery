package com.example.photogallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.photogallery.databinding.FragmentImageDetailsBinding
import com.example.photogallery.models.Result


class ImageDetailsFragment : Fragment() {
    private lateinit var binding: FragmentImageDetailsBinding
    private lateinit var image: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(image.url)
            .into(binding.detailImageView)

        with(binding){
            titleTextView.text = image.title
            creatorTextView.text = "Creator: ${image.creator ?: "Unknown"}"
            licenceTextView.text = "Licence: ${image.license} ${image.license_version ?: ""}"
            dimensionsTextView.text = "Dimensions: ${image.width ?: "?"} x ${image.height ?: "?"}"
            sourceTextView.text = "Source: ${image.source ?: "Unknown"}"
            closeButton.setOnClickListener{
                parentFragmentManager.popBackStack()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(image: Result): ImageDetailsFragment {
            val fragment = ImageDetailsFragment()
            fragment.image = image
            return fragment
        }

    }
}