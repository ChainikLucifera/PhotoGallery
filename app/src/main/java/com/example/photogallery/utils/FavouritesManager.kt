package com.example.photogallery.utils

import com.example.photogallery.models.Result

object FavouritesManager {
    private val favourites = mutableSetOf<Result>()

    fun addToFavourites(image: Result) {
        favourites.add(image)
    }

    fun removeFromFavourites(imageID: String) {
        favourites.removeIf { currentImage ->
            currentImage.id == imageID
        }
    }

    fun isFavorite(imageID: String): Boolean {
        return favourites.any { currentImage ->
            currentImage.id == imageID
        }
    }

    fun getFavourites() : List<Result>{
        return favourites.toList()
    }
}