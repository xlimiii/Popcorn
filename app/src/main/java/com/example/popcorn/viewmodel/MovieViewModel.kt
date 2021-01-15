package com.example.popcorn.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.popcorn.model.api.ApiRepository
import com.example.popcorn.model.api.ApiRequest
import com.example.popcorn.model.Movie
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : ApiRepository = ApiRepository(ApiRequest.getAPI())

    var currentMovieID : Int? = null
    fun setCurrentMovieID(id : Int) { currentMovieID = id; }

    var currentMovie = MutableLiveData<Movie>()
    fun setCurrentMovie()
    {
        viewModelScope.launch {
            val response = repository.getMovieDetails(currentMovieID!!).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                currentMovie.value = data
            }
        }
    }
}