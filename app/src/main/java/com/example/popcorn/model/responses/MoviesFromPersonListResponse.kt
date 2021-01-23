package com.example.popcorn.model.responses

import com.example.popcorn.model.Movie
import com.example.popcorn.model.Person

data class MoviesFromPersonListResponse(
    val id : Int,
    val cast : List<Movie>,
    val crew : List<Movie>
    )