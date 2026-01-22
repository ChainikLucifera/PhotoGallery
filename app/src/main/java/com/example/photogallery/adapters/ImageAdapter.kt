package com.example.photogallery.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photogallery.R
import com.example.photogallery.databinding.ItemImageBinding
import com.example.photogallery.models.Result
import com.example.photogallery.utils.FavouritesManager
import com.example.photogallery.utils.extensions.hideKeyboard

class ImageAdapter(
    private val images: List<Result>,
    private val onItemClick: (Result) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    var onFavouriteClick: ((Result, Boolean) -> Unit)? = null

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemImageBinding.bind(view)

        val imageView: ImageView = binding.imageView
        val titleView: TextView = binding.titleTV
        val licenceView: TextView = binding.licenceTV
        val favouriteButton: ImageButton = binding.favouriteButton
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

        updateFavoriteButton(holder.favouriteButton, image.id)

        holder.favouriteButton.setOnClickListener {
            val isFavourite = FavouritesManager.isFavorite(image.id)
            onFavouriteClick?.invoke(image, !isFavourite)
        }

        holder.itemView.setOnClickListener {
            onItemClick(image)
            it.hideKeyboard()
        }
    }

    private fun updateFavoriteButton(button: ImageButton, imageID: String){
        val isFavourite = FavouritesManager.isFavorite(imageID)
        val iconRes = if(isFavourite){
            android.R.drawable.btn_star_big_on
        }
        else {
            android.R.drawable.btn_star_big_off
        }

        button.setImageResource(iconRes)
    }


}