package com.example.popcorn.model

data class ProductionCompany (
    val description : String?,
    val headquarters : String?,
    val homepage : String?,
    val id : Int,
    val logo_path : String?,
    val name : String,
    val origin_country : String?,
    val parent_company : ProductionCompany?
)