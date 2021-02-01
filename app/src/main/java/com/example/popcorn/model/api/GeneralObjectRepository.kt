package com.example.popcorn.model.api

import com.example.popcorn.model.responses.GeneralObjectListResponse
import retrofit2.Call

class GeneralObjectRepository(private val apiRequest : ApiRequest) {
    fun searchForObjects(someText : String) : Call<GeneralObjectListResponse> = apiRequest.searchMulti(someText)
}