package com.example.popcorn.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.popcorn.model.Genre
import com.example.popcorn.model.api.MovieRepository
import com.example.popcorn.model.api.ApiRequest
import com.example.popcorn.model.Movie
import com.example.popcorn.model.Person
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : MovieRepository = MovieRepository(ApiRequest.getAPI())

    //                                      MOVIE SEARCH
    var moviesWithMatchingTitle = MutableLiveData<List<Movie>>()
    fun setMoviesWithMatchingTitle(givenText : String)
    {
        viewModelScope.launch {
            val response = repository.searchForMovies(givenText).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                moviesWithMatchingTitle.value = data.results
            }
        }
    }

    //                                     POPULAR MOVIES
    var popularMovies = MutableLiveData<List<Movie>>()
    fun setPopularMovies()
    {
        viewModelScope.launch {
            val response = repository.getPopularMovies().awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                popularMovies.value = data.results
            }
        }
    }

    //                                      MOVIE DETAILS
    var currentMovie = MutableLiveData<Movie>()
    fun setCurrentMovie(currentMovieID : Int)
    {
        viewModelScope.launch {
            val response = repository.getMovieDetails(currentMovieID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                currentMovie.value = data
            }
        }
    }

    //                            PEOPLE CONNECTED WITH THIS MOVIE
    var peopleConnectedWithCurrentMovie = MutableLiveData<List<Person>>()
    fun setPeopleConnectedWithCurrentMovie(currentMovieID : Int)
    {
        viewModelScope.launch {
            val response = repository.getPeopleFromThisMovie(currentMovieID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                peopleConnectedWithCurrentMovie.value = data.cast
            }
        }
    }

    //                                         GENRES
    var genres = MutableLiveData<List<Genre>>()
    fun setGenres()
    {
        viewModelScope.launch {
            val response = repository.getAllGenres().awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                genres.value = data.genres
            }
        }
    }
}