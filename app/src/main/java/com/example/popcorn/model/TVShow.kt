package com.example.popcorn.model

data class TVShow(
    val backdrop_path : String,
    val created_by : List<Person>,
    val run_time: List<Int>,
    val first_air_date: String,
    val last_air_date: String,
    val genres : List<Genre>,
    val genre_ids : List<Int>,
    val id : Int,
    val last_episode_aired: Episode,
    val name: String,
    val networks : List<Network>,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val original_language : String,
    val original_name : String,
    val overview : String,
    val popularity: Double,
    val poster_path: String,
    val production_companies : List<ProductionCompany>,
    val production_countries : List<ProductionCountry>,
    val seasons: List<Season>,
    val spoken_languages : List<SpokenLanguage>,
    val vote_average : Double,
    val vote_count : Int,
    //cast and crew
//Crew and Cast
    val credit_id : String,
    val department : String,
    val job : String,
    val character : String,
    val order : Int
)




