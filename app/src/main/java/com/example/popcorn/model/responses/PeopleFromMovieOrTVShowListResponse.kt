package com.example.popcorn.model.responses

import com.example.popcorn.model.Person

// Used in movie details and tv show details - consists of lists of people that played in or were in crew of specific movie / tv show:
data class PeopleFromMovieOrTVShowListResponse(val id : Int, val cast : List<Person>, val crew : List<Person>)