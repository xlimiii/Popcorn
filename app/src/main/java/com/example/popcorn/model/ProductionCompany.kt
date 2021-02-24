package com.example.popcorn.model

data class ProductionCompany (
    val id : Int,
    val name : String,

    val homepage : String?,    // url leading to website
    val logo_path : String?,   // url with image

    val origin_country : String?,
    val headquarters : String?
)