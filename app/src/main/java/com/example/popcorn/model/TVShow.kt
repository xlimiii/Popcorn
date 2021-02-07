package com.example.popcorn.model

data class TVShow(
    val first_air_date: String?,
    val last_air_date: String,
    val genres : List<Genre>,
    val id : Int,
    val name: String,
    val overview : String?,
    val popularity: Double,
    val poster_path: String?,
    val production_companies : List<ProductionCompany>,
    val spoken_languages : List<SpokenLanguage>,
    // Cast and Crew:
    val character : String?,
    val department: String?
)




