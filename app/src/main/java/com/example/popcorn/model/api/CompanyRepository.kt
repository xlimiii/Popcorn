package com.example.popcorn.model.api

import com.example.popcorn.model.ProductionCompany
import com.example.popcorn.model.responses.CompanyListResponse
import retrofit2.Call

class CompanyRepository(private val apiRequest : ApiRequest) {
    fun searchForCompanies(someText : String) : Call<CompanyListResponse> = apiRequest.searchForCompanies(someText)
    fun getCompanyDetails(companyID : Int) : Call<ProductionCompany> = apiRequest.getCompanyDetails(companyID)
}