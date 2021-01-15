package com.example.popcorn.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_table")
data class Favourite(@PrimaryKey(autoGenerate = true) val id: Int, val movieID : Int, val date : String)