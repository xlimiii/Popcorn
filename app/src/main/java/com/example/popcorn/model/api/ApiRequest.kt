package com.example.popcorn.model.api

import com.example.popcorn.model.*
import com.example.popcorn.model.responses.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequest {

    //                                  MOVIES SECTION
    @GET("3/search/movie?api_key=$apiKey")
    fun searchForMovies(@Query("query") someText : String) : Call<MovieListResponse>

    @GET("3/movie/popular?api_key=$apiKey")
    fun getPopularMovies() : Call<MovieListResponse>

    @GET("3/movie/{movieID}?api_key=$apiKey")
    fun getMovieDetails(@Path("movieID") movieID : Int) : Call<Movie>

    @GET("3/movie/{movieID}/credits?api_key=$apiKey")
    fun getPeopleFromThisMovie(@Path("movieID") movieID : Int) : Call<PeopleFromMovieOrTVShowListResponse>

    @GET("3/genre/movie/list?api_key=$apiKey")
    fun getAllGenres() : Call<GenreListResponse>



    //                                 TV SHOWS SECTION
    @GET("3/search/tv?api_key=$apiKey")
    fun searchForTVShows(@Query("query") someText : String) : Call<TVShowListResponse>

    @GET("3/tv/popular?api_key=$apiKey")
    fun getPopularTVShows() : Call<TVShowListResponse>

    @GET("3/tv/{tvShowID}?api_key=$apiKey")
    fun getTVShowDetails(@Path("tvShowID") tvShowID : Int) : Call<TVShow>

    @GET("3/tv/{tvShowID}/credits?api_key=$apiKey")
    fun getPeopleFromThisTVShow(@Path("tvShowID") tvShowID : Int) : Call<PeopleFromMovieOrTVShowListResponse>



    //                                  PEOPLE SECTION
    @GET("3/search/person?api_key=$apiKey")
    fun searchForPeople(@Query("query") someText : String) : Call<PersonListResponse>

    @GET("3/person/popular?api_key=$apiKey")
    fun getPopularPeople() : Call<PersonListResponse>

    @GET("3/person/{personID}?api_key=$apiKey")
    fun getPersonDetails(@Path("personID") personID : Int) : Call<Person>

    @GET("3/person/{personID}/movie_credits?api_key=$apiKey")
    fun getMoviesFromThisPerson(@Path("personID") personID : Int) : Call<MoviesFromPersonListResponse>



    //                                  COMPANIES SECTION
    @GET("3/search/company?api_key=$apiKey")
    fun searchForCompanies(@Query("query") someText : String) : Call<CompanyListResponse>

    @GET("3/company/{companyID}?api_key=$apiKey")
    fun getCompanyDetails(@Path("companyID") companyID : Int) : Call<ProductionCompany>

    //                                  GENERAL SECTION
    @GET("3/search/multi?api_key=$apiKey")
    fun searchMulti(@Query("query") someText : String) : Call<GeneralObjectListResponse>


    //                  INSTANCE WHICH PROVIDES COMMUNICATION WITH API
    companion object {
        private const val apiKey: String = "055b1da364a8c6b64b59a86724d0ae7c"
        private const val WEBSITE = "https://api.themoviedb.org/"
        private var INSTANCE : ApiRequest? = null

        fun getAPI() : ApiRequest {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance
            else
            {
                val comm = Retrofit.Builder()
                    .baseUrl(WEBSITE).addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiRequest::class.java)
                INSTANCE = comm
                return comm
            }
        }
    }
}