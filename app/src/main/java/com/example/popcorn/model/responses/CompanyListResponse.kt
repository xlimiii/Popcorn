package com.example.popcorn.model.responses

import com.example.popcorn.model.ProductionCompany

data class CompanyListResponse(val page : Int, val results : List<ProductionCompany>)