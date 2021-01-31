package com.example.popcorn.model.responses

import com.example.popcorn.model.Person

data class PeopleFromMovieOrTVShowListResponse(val id : Int, val cast : List<Person>, val crew : List<Person>)