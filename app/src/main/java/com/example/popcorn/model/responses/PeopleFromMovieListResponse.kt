package com.example.popcorn.model.responses

import com.example.popcorn.model.Person

data class PeopleFromMovieListResponse(val id : Int, val cast : List<Person>)