package com.example.popcorn.model

data class TVShow (
    // General:
    val id : Int,
    val name : String,
    val poster_path : String?, // poster
    val popularity : Double,

    // Details:
    val vote_average : Double?,
    val episode_run_time : List<Int>,
    val overview : String?,
    val genres : List<Genre>,
    val first_air_date: String?,
    val last_air_date: String?,
    val production_companies : List<ProductionCompany>,
    val spoken_languages : List<SpokenLanguage>,

    // Who was played / for what was responsible - used in recycler views:
    val character : String?,
    val department: String?
)




