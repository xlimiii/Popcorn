package com.example.popcorn.model.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouriteDao {
    @Insert
    suspend fun insertFavourite(fav : Favourite)

    @Delete
    suspend fun deleteFavourite(fav : Favourite)

    @Query("SELECT * FROM favourites_table ORDER BY id DESC")
    fun getAllFavourites() : LiveData<List<Favourite>>
}