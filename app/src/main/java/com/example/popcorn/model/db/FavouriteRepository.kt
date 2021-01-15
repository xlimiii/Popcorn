package com.example.popcorn.model.db

import androidx.lifecycle.LiveData

class FavouriteRepository(private val favDao: FavouriteDao) {
    val readAll : LiveData<List<Favourite>> = favDao.getAllFavourites()
    suspend fun add(fav : Favourite) = favDao.insertFavourite(fav)
    suspend fun delete(fav : Favourite) = favDao.deleteFavourite(fav)
}