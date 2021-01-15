package com.example.popcorn.Model

data class Movie (

    val adult : Boolean,
    val backdrop_path : String,
    val belongs_to_collection : String,
    val budget : Int,
    val genres : List<Genres>,
    val homepage : String,
    val id : Int,
    val imdb_id : String,
    val original_language : String,
    val original_title : String,
    val overview : String,
    val popularity : Double,
    val poster_path : String,
    val production_companies : List<Production_companies>,
    val production_countries : List<Production_countries>,
    val release_date : String,
    val revenue : Int,
    val runtime : Int,
    val spoken_languages : List<Spoken_languages>,
    val status : String,
    val tagline : String,
    val title : String,
    val video : Boolean,
    val vote_average : Double,
    val vote_count : Int
)