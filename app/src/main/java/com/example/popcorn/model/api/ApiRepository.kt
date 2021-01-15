package com.example.popcorn.model.api

import com.example.popcorn.model.Movie
import retrofit2.Call

class ApiRepository(private val apiRequest : ApiRequest) {
    fun getMovieDetails(movieID : Int) : Call<Movie> = apiRequest.getMovieDetails(movieID)
}