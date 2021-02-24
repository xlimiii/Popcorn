package com.example.popcorn.model

data class Person (
    // General:
    val id : Int,
    val name : String,
    val gender : Int,
    val profile_path : String?, // photo
    val popularity : Double,

    // Details:
    val biography: String?,
    val birthday: String?,
    val deathDay: String?,
    val place_of_birth: String?,
    val known_for_department : String, // acting, directing, etc.

    // Who was played / for what was responsible - used in recycler views:
    val character : String?,
    val department: String?
)