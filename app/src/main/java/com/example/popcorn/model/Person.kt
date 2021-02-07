package com.example.popcorn.model

data class Person (
    val birthday: String?,
    val deathday: String?,
    val biography: String?,
    val gender : Int,
    val id : Int,
    val known_for_department : String,
    val place_of_birth: String?,
    val name : String,
    val popularity : Double,
    val profile_path : String?,
    // Cast and Crew:
    val character : String?,
    val department: String?
)