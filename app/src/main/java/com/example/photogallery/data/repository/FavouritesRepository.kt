package com.example.photogallery.data.repository

import android.content.Context
import com.example.photogallery.data.database.AppDatabase
import com.example.photogallery.models.Result
import com.example.photogallery.utils.extensions.toFavouriteImage
import com.example.photogallery.utils.extensions.toResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouritesRepository(context: Context) {
    private val dao = AppDatabase.getInstance(context).favouriteImageDao()

    suspend fun addToFavourites(image: Result) {
        dao.insert(image.toFavouriteImage())
    }

    suspend fun removeFromFavourites(imageID: String) {
        dao.deleteByID(imageID)
    }

    suspend fun isFavourite(imageID: String) = dao.isFavourite(imageID)

    fun getFavouritesStream(): Flow<List<Result>> {
        return dao.getAll().map { favouriteImages ->
            favouriteImages.map {
                it.toResult()
            }
        }
    }

    fun getFavouriteIDsStream(): Flow<Set<String>> {
        return dao.getAll().map { favouriteImages ->
            favouriteImages.map {
                it.id
            }.toSet()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: FavouritesRepository? = null

        fun getInstance(context: Context) : FavouritesRepository {
            return  INSTANCE ?: synchronized(this) {
                FavouritesRepository(context).also { INSTANCE = it }
            }
        }
    }

}