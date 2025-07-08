package com.example.photogallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.photogallery.databinding.ItemImageBinding
import com.example.photogallery.models.Result

class ImageAdapter(private val images: List<Result>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : ViewHolder(view) {
        private val binding = ItemImageBinding.bind(view)

        val imageView: ImageView = binding.imageView
        val titleView: TextView = binding.titleTV
        val licenceView: TextView = binding.licenceTV
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount() = images.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        Glide.with(holder.imageView.context).load(image.url).into(holder.imageView)

        holder.titleView.text = image.title
        holder.licenceView.text = "Licence: ${image.license}"
    }


}