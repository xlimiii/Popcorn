package com.example.popcorn.model.api

import com.example.popcorn.model.Person
import com.example.popcorn.model.responses.MoviesFromPersonListResponse
import com.example.popcorn.model.responses.PersonListResponse
import retrofit2.Call

class PersonRepository(private val apiRequest : ApiRequest) {
    fun searchForPeople(someText : String) : Call<PersonListResponse> = apiRequest.searchForPeople(someText)
    fun getPopularPeople() : Call<PersonListResponse> = apiRequest.getPopularPeople()
    fun getPersonDetails(personID : Int) : Call<Person> = apiRequest.getPersonDetails(personID)
    fun getMoviesFromThisPerson(personID : Int) : Call<MoviesFromPersonListResponse> = apiRequest.getMoviesFromThisPerson(personID)
}