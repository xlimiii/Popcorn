package com.example.popcorn.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.popcorn.model.GeneralObject
import com.example.popcorn.model.Person
import com.example.popcorn.model.api.ApiRequest
import com.example.popcorn.model.api.PersonRepository
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class PersonViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : PersonRepository = PersonRepository(ApiRequest.getAPI())

    //                             PERSON SEARCH AND POPULAR PEOPLE
    var peopleWithMatchingName = MutableLiveData<List<Person>>()
    fun setPeopleWithMatchingName(givenText : String)
    {
        viewModelScope.launch {
            // if there is no input, list changes into list of popular people;
            // otherwise this is list of people with matching names
            val response =
                    if (givenText != "") { repository.searchForPeople(givenText).awaitResponse() }
                    else { repository.getPopularPeople().awaitResponse() }

            if (response.isSuccessful)
            {
                val data = response.body()!!
                peopleWithMatchingName.value = data.results.sortedByDescending { it.popularity }
            }
        }
    }

    //                                      PERSON DETAILS
    var currentPerson = MutableLiveData<Person>()
    fun setCurrentPerson(currentPersonID : Int)
    {
        viewModelScope.launch {
            val response = repository.getPersonDetails(currentPersonID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                currentPerson.value = data
            }
        }
    }

    //                         MOVIES AND TV SHOWS CONNECTED WITH THIS PERSON
    var currentPersonInCastCollection = MutableLiveData<List<GeneralObject>>()
    var currentPersonInCrewCollection = MutableLiveData<List<GeneralObject>>()
    fun setCurrentPersonCollection(currentPersonID : Int)
    {
        viewModelScope.launch {
            val response = repository.getMoviesAndTVShowsFromThisPerson(currentPersonID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                currentPersonInCastCollection.value = data.cast.sortedByDescending { it.popularity }
                currentPersonInCrewCollection.value = data.crew.sortedByDescending { it.popularity }
            }
        }
    }
}