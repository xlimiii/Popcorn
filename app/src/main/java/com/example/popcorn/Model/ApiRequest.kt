package com.example.popcorn.Model

import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {

    @GET("3/movie/550?api_key=055b1da364a8c6b64b59a86724d0ae7c&language=pl-PL")
    fun getMovieDetails(): Call<Movie>

}