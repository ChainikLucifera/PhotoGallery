package com.example.photogallery.data.database

import android.content.Context
import androidx.annotation.Nullable
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.photogallery.data.dao.FavouriteImageDao
import com.example.photogallery.data.entities.FavouriteImage

@Database(
    entities = [FavouriteImage::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase(){

    abstract fun favouriteImageDao() : FavouriteImageDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "photo_gallery_database")
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
