package com.example.telstrademo.data.api

class ApiHelper (private val apiService: ApiService) {

    suspend fun getFactDetails() = apiService.getFactDetails()
}