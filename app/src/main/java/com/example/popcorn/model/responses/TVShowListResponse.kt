package com.example.popcorn.model.responses

import com.example.popcorn.model.TVShow

// Used in getting list of popular tv shows and tv shows with matching title:
data class TVShowListResponse(val results : List<TVShow>)