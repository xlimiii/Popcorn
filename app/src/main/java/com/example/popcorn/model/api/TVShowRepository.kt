package com.example.popcorn.model.api

import com.example.popcorn.model.TVShow
import com.example.popcorn.model.responses.PeopleFromMovieOrTVShowListResponse
import com.example.popcorn.model.responses.TVShowListResponse
import retrofit2.Call

// Functions connected with TVShow objects - general and details:
class TVShowRepository(private val apiRequest : ApiRequest) {
    fun searchForTVShows(someText : String) : Call<TVShowListResponse> = apiRequest.searchForTVShows(someText)
    fun getPopularTVShows() : Call<TVShowListResponse> = apiRequest.getPopularTVShows()
    fun getTVShowDetails(TVShowID : Int) : Call<TVShow> = apiRequest.getTVShowDetails(TVShowID)
    fun getPeopleFromThisTVShow(TVShowID: Int) : Call<PeopleFromMovieOrTVShowListResponse> = apiRequest.getPeopleFromThisTVShow(TVShowID)
}