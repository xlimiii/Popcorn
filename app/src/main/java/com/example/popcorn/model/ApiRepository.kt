package com.example.popcorn.model

import retrofit2.Call

class ApiRepository(private val apiRequest : ApiRequest) {
    fun getMovieDetails(movieID : Int) : Call<Movie> = apiRequest.getMovieDetails(movieID)
}