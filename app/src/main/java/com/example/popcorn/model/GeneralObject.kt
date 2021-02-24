package com.example.popcorn.model

// One class for movies and tv shows - used in lists with Movies And TVShows From Person:
data class GeneralObject (
    // General:
    val id : Int,
    val media_type: String,    // tv or movie
    val poster_path : String,  // poster
    val popularity : Double,

    // Where?
    val title : String,        // movie
    val name : String,         // tv show

    // As who?
    val character : String,    // movie's or tv show's cast member - who was played
    val department: String     // movie's or tv show's crew member - for what was responsible
)