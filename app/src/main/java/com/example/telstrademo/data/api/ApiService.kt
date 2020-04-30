package com.example.telstrademo.data.api

import com.example.telstrademo.data.model.MainResponse
import retrofit2.http.GET

/* Used to get response facts.json from api by calling get suspend function */
interface ApiService {

    @GET("facts.json")
    suspend fun getFactDetails(): MainResponse
}