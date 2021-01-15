package com.example.popcorn.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequest {

    @GET("3/movie/{movieID}?api_key=055b1da364a8c6b64b59a86724d0ae7c&language=en-US") //550
    fun getMovieDetails(@Path("movieID") movieID : Int): Call<Movie>

    companion object {
        private var INSTANCE : ApiRequest? = null
        private const val WEBSITE = "https://api.themoviedb.org/"

        fun getAPI() : ApiRequest {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            else
            {
                val comm = Retrofit.Builder()
                    .baseUrl(WEBSITE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiRequest::class.java)
                INSTANCE = comm
                return comm
            }
        }
    }
}