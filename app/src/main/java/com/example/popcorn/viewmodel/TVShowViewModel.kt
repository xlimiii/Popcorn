package com.example.popcorn.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.popcorn.model.Person
import com.example.popcorn.model.TVShow
import com.example.popcorn.model.api.ApiRequest
import com.example.popcorn.model.api.TVShowRepository
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class TVShowViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : TVShowRepository = TVShowRepository(ApiRequest.getAPI())

    //                                TV SHOW SEARCH AND POPULAR TV SHOWS
    var TVShowsWithMatchingTitle = MutableLiveData<List<TVShow>>()
    fun setTVShowsWithMatchingTitle(givenText : String)
    {
        viewModelScope.launch {
            // if there is no input, list changes into list of popular TV shows;
            // otherwise this is list of TV shows with matching names
            val response =
                    if (givenText != "") { repository.searchForTVShows(givenText).awaitResponse() }
                    else { repository.getPopularTVShows().awaitResponse() }

            if (response.isSuccessful)
            {
                val data = response.body()!!
                TVShowsWithMatchingTitle.value = data.results.sortedByDescending { it.popularity }
            }
        }
    }

    //                                      TV SHOW DETAILS
    var currentTVShow = MutableLiveData<TVShow>()
    fun setCurrentTVShow(currentTVShowID : Int)
    {
        viewModelScope.launch {
            val response = repository.getTVShowDetails(currentTVShowID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                currentTVShow.value = data
            }
        }
    }

    //                            PEOPLE CONNECTED WITH THIS TV SHOW
    var currentTVShowCast = MutableLiveData<List<Person>>()
    var currentTVShowCrew = MutableLiveData<List<Person>>()
    fun setPeopleConnectedWithCurrentTVShow(currentTVShowID : Int)
    {
        viewModelScope.launch {
            val response = repository.getPeopleFromThisTVShow(currentTVShowID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                currentTVShowCast.value = data.cast.sortedByDescending { it.popularity }
                currentTVShowCrew.value = data.crew.sortedByDescending { it.popularity }
            }
        }
    }
}