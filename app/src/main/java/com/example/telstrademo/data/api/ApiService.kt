package com.example.telstrademo.data.api

import com.example.telstrademo.data.model.MainResponse
import retrofit2.http.GET

interface ApiService {

    @GET("facts.json")
    suspend fun getFactDetails(): MainResponse
}