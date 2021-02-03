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

    //                           MOVIE SEARCH AND POPULAR MOVIES
    var moviesWithMatchingTitle = MutableLiveData<List<Movie>>()
    fun setMoviesWithMatchingTitle(givenText : String)
    {
        viewModelScope.launch {
            // if there is no input, list changes into list of popular movies;
            // otherwise this is list of movies with matching names
            val response =
                    if (givenText != "") { repository.searchForMovies(givenText).awaitResponse() }
                    else { repository.getPopularMovies().awaitResponse() }

            if (response.isSuccessful)
            {
                val data = response.body()!!
                moviesWithMatchingTitle.value = data.results.sortedByDescending { it.popularity }
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
    var currentMovieCast = MutableLiveData<List<Person>>()
    var currentMovieCrew = MutableLiveData<List<Person>>()
    fun setPeopleConnectedWithCurrentMovie(currentMovieID : Int)
    {
        viewModelScope.launch {
            val response = repository.getPeopleFromThisMovie(currentMovieID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                currentMovieCast.value = data.cast.sortedByDescending { it.popularity }
                currentMovieCrew.value = data.crew.sortedByDescending { it.popularity }
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