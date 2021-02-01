package com.example.popcorn.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.popcorn.model.GeneralObject
import com.example.popcorn.model.Movie
import com.example.popcorn.model.Person
import com.example.popcorn.model.api.ApiRequest
import com.example.popcorn.model.api.PersonRepository
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class PersonViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : PersonRepository = PersonRepository(ApiRequest.getAPI())

    //                                      PERSON SEARCH
    var peopleWithMatchingName = MutableLiveData<List<Person>>()
    fun setPeopleWithMatchingName(givenText : String)
    {
        viewModelScope.launch {
            val response = repository.searchForPeople(givenText).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                peopleWithMatchingName.value = data.results
            }
        }
    }

    //                                     POPULAR PEOPLE
    var popularPeople = MutableLiveData<List<Person>>()
    fun setPopularPeople()
    {
        viewModelScope.launch {
            val response = repository.getPopularPeople().awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                popularPeople.value = data.results
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

    //                            MOVIES CONNECTED WITH THIS PERSON
    var moviesConnectedWithCurrentPerson = MutableLiveData<List<Movie>>()
    fun setMoviesConnectedWithCurrentPerson(currentPersonID : Int)
    {
        viewModelScope.launch {
            val response = repository.getMoviesFromThisPerson(currentPersonID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                moviesConnectedWithCurrentPerson.value = data.cast
            }
        }
    }


    //                         MOVIES AND TV SHOWS CONNECTED WITH THIS PERSON
    var moviesAndTVShowsConnectedWithCurrentPerson = MutableLiveData<List<GeneralObject>>()
    fun setMoviesAndTVShowsConnectedWithCurrentPerson(currentPersonID : Int)
    {
        viewModelScope.launch {
            val response = repository.getMoviesAndTVShowsFromThisPerson(currentPersonID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                moviesAndTVShowsConnectedWithCurrentPerson.value = data.cast
            }
        }
    }
}