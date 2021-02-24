package com.example.popcorn.model.api

import com.example.popcorn.model.ProductionCompany
import retrofit2.Call

// Functions connected with Company objects:
class CompanyRepository(private val apiRequest : ApiRequest) {
    fun getCompanyDetails(companyID : Int) : Call<ProductionCompany> = apiRequest.getCompanyDetails(companyID)
}