package com.example.popcorn.model

data class Episode(
    val air_date : String,
    val number : Int,
    val id : Int,
    val name : String,
    val overview: String,
    val season_number: Int,
    val vote_average: Double,
    val vote_count: Int
)
