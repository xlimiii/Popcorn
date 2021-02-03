package com.example.popcorn.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.popcorn.model.Movie
import com.example.popcorn.model.TVShow
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
    fun addFavourite(movie : Movie)
    {
        viewModelScope.launch { repository.add(
            Favourite(id = 0, movieOrTVShowID = movie.id, title = movie.title, media_type = "movie",
                    poster_path = movie.poster_path ?: "", release_date = movie.release_date,
                    date = SimpleDateFormat("dd-MM-yyyy").format(Date()))
        )}
    }

    @SuppressLint("SimpleDateFormat")
    fun addFavourite(tvs : TVShow)
    {
        viewModelScope.launch { repository.add(
                Favourite(id = 0, movieOrTVShowID = tvs.id, title = tvs.name, media_type = "tv",
                        poster_path = tvs.poster_path ?: "", release_date = tvs.first_air_date,
                        date = SimpleDateFormat("dd-MM-yyyy").format(Date()))
        )}
    }

    fun deleteFavorite(favID : Int)
    {
        val thisFav = favourites.value!!.find { x -> x.id == favID }
        viewModelScope.launch {
            if (thisFav != null) { repository.delete(thisFav) }
        }
    }
}