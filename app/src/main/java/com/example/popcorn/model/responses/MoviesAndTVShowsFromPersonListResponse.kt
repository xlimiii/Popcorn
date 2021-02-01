package com.example.popcorn.model.responses

import com.example.popcorn.model.GeneralObject

class MoviesAndTVShowsFromPersonListResponse(val id : Int, val cast : List<GeneralObject>, val crew : List<GeneralObject>)