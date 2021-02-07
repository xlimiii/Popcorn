package com.example.popcorn.model

data class Movie (
    val genres : List<Genre>,
    val id : Int,
    val media_type : String,
    val overview : String?,
    val popularity : Double,
    val poster_path : String?,
    val production_companies : List<ProductionCompany>,
    val release_date : String?,
    val spoken_languages : List<SpokenLanguage>,
    val title : String,
    // Cast and Crew:
    val character : String?,
    val department: String?
)