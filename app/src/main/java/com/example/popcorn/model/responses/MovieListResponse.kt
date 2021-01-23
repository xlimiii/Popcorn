package com.example.popcorn.model.responses

import com.example.popcorn.model.Movie

data class MovieListResponse(
    val page : Int,
    val results : List<Movie>,
    val total_pages : Int,
    val total_results : Int
    )