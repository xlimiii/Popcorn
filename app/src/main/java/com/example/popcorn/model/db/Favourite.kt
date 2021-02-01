package com.example.popcorn.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_table")
data class Favourite(
        @PrimaryKey(autoGenerate = true) val id: Int,
        val media_type : String,
        val movieOrTVShowID : Int,
        val title: String,
        val poster_path : String,
        val release_date : String,
        val date : String
)