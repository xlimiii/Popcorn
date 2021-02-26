package com.example.popcorn.model

data class Movie (
    // General:
    val id : Int,
    val title : String,
    val poster_path : String?, // poster
    val popularity : Double,

    // Details:
    val vote_average : Double?,
    val runtime: Int?,
    val overview : String?,    // description
    val genres : List<Genre>,
    val release_date : String?,
    val production_companies : List<ProductionCompany>,
    val spoken_languages : List<SpokenLanguage>,

    // Who was played / for what was responsible - used in recycler views:
    val character : String?,
    val department: String?
)