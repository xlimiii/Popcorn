package com.example.popcorn.model.responses

import com.example.popcorn.model.Movie

data class MoviesFromPersonListResponse(val id : Int, val cast : List<Movie>, val crew : List<Movie>)