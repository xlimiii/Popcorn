package com.example.popcorn.model

data class Person (
    val birthday: String,
    val deathday: String,
    val biography: String,
    val adult : Boolean,
    val gender : Int,
    val id : Int,
    val known_for : List<Movie>,
    val known_for_department : String,
    val place_of_birth: String,
    val name : String,
    val original_name : String,
    val popularity : Double,
    val profile_path : String,
    //Cast & Crew
    val cast_id : Int,
    val character : String,
    val credit_id : String,
    val order : Int,
    val department : String,
    val job : String
    )