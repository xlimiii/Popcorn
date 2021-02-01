package com.example.popcorn.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.popcorn.model.GeneralObject
import com.example.popcorn.model.Movie
import com.example.popcorn.model.ProductionCompany
import com.example.popcorn.model.api.ApiRequest
import com.example.popcorn.model.api.CompanyRepository
import com.example.popcorn.model.api.GeneralObjectRepository
import com.example.popcorn.model.api.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class MultiViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : GeneralObjectRepository = GeneralObjectRepository(ApiRequest.getAPI())
    private val movieRepository : MovieRepository = MovieRepository(ApiRequest.getAPI())


    //                                      COMPANY SEARCH
    var objectsWithMatchingName = MutableLiveData<List<GeneralObject>>()
    fun setObjectsWithMatchingName(givenText : String)
    {
        viewModelScope.launch {
            val response = repository.searchForObjects(givenText).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                objectsWithMatchingName.value = data.results
            }
        }
    }
    var currentObject = MutableLiveData<GeneralObject>()
    var currentMovie = MutableLiveData<Movie>()
    fun setCurrentObject(currentObjectID : Int, mediaType:String)
    {
        if(mediaType == "movie"){
        viewModelScope.launch {
            val response = movieRepository.getMovieDetails(currentObjectID).awaitResponse()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                currentMovie.value = data
            }
            }
        }
    }
}