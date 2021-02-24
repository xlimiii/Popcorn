package com.example.popcorn.model.responses

import com.example.popcorn.model.Person

// Used in getting list of popular people and people with matching name:
class PersonListResponse(val results : List<Person>)