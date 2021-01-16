package com.example.popcorn.model.api

import com.example.popcorn.model.*
import com.example.popcorn.model.responses.GenreListResponse
import com.example.popcorn.model.responses.MovieListResponse
import com.example.popcorn.model.responses.PeopleFromMovieListResponse
import retrofit2.Call

class MovieRepository(private val apiRequest : ApiRequest) {
    fun searchForMovies(someText : String) : Call<MovieListResponse> = apiRequest.searchForMovies(someText)
    fun getPopularMovies() : Call<MovieListResponse> = apiRequest.getPopularMovies()
    fun getMovieDetails(movieID : Int) : Call<Movie> = apiRequest.getMovieDetails(movieID)
    fun getPeopleFromThisMovie(movieID: Int) : Call<PeopleFromMovieListResponse> = apiRequest.getPeopleFromThisMovie(movieID)
    fun getAllGenres() : Call<GenreListResponse> = apiRequest.getAllGenres()
}