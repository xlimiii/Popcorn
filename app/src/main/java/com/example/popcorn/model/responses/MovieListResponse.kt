package com.example.popcorn.model.responses

import com.example.popcorn.model.Movie

// Used in getting list of popular movies and movies with matching title:
data class MovieListResponse(val results : List<Movie>)