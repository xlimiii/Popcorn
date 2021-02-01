package com.example.popcorn.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.popcorn.model.Movie
import com.example.popcorn.model.ProductionCompany
import com.example.popcorn.model.api.ApiRequest
import com.example.popcorn.model.api.CompanyRepository
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class CompanyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : CompanyRepository = CompanyRepository(ApiRequest.getAPI())

    //                                      COMPANY DETAILS
    var currentCompany = MutableLiveData<ProductionCompany>()
    fun setCurrentCompany(currentCompanyID : Int)
    {
        viewModelScope.launch {
            val response = repository.getCompanyDetails(currentCompanyID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                currentCompany.value = data
            }
        }
    }
}