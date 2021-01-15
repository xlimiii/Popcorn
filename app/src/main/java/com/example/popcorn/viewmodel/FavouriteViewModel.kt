package com.example.popcorn.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.popcorn.model.db.Favourite
import com.example.popcorn.model.db.FavouriteRepository
import com.example.popcorn.model.db.PopcornDatabase
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FavouriteViewModel(application: Application) : AndroidViewModel(application) {
    private val database = PopcornDatabase.getDatabase(application)
    private val repository : FavouriteRepository = FavouriteRepository(database.favouriteDao())
    val favourites : LiveData<List<Favourite>> = repository.readAll

    @SuppressLint("SimpleDateFormat")
    fun addFavourite(movieID : Int)
    {
        viewModelScope.launch { repository.add(
            Favourite(id = 0, movieID = movieID,
                date = SimpleDateFormat("dd-MM-yyyy").format(Date()))
        )}
    }

    fun deleteFavorite(movieID : Int)
    {
        val thisFav = favourites.value!!.find { x -> x.movieID == movieID }
        viewModelScope.launch {
            if (thisFav != null) { repository.delete(thisFav) }
        }
    }
}