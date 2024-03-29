package com.example.popcorn.model.api

import com.example.popcorn.model.*
import com.example.popcorn.model.responses.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// Interface responsible for communicating with TMDB RESTAPI:
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

    @GET("3/person/{personID}/combined_credits?api_key=$apiKey")
    fun getMoviesAndTVShowsFromThisPerson(@Path("personID") personID : Int) : Call<MoviesAndTVShowsFromPersonListResponse>



    //                                  COMPANIES SECTION
    @GET("3/company/{companyID}?api_key=$apiKey")
    fun getCompanyDetails(@Path("companyID") companyID : Int) : Call<ProductionCompany>



    //                  INSTANCE WHICH PROVIDES COMMUNICATION WITH API
    companion object {
        private const val apiKey: String = "YOUR_API_KEY"    // PLACE YOUR API KEY HERE
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