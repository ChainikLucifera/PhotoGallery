package com.example.photogallery.data.dao
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.photogallery.models.Result
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(image: Result)

    @Delete
    suspend fun delete(image: Result)

    @Query("DELETE FROM `Favourite Images` WHERE id = :imageId")
    suspend fun deleteByID(imageId: String)

    @Query("SELECT * FROM `Favourite Images` ORDER BY id")
    fun getAll() : Flow<List<Result>>

    @Query("SELECT * FROM `Favourite Images` WHERE id = :imageId")
    suspend fun getById(imageId: String) : Result?

    @Query("SELECT EXISTS(SELECT 1 FROM `Favourite Images` WHERE id = :imageId)")
    suspend fun isFavourite(imageId: String) : Boolean
}