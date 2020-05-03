package com.example.telstrademo.data.api

/*API Helper class to help us with the ApiService call*/
class ApiHelper(private val apiService: ApiService?) {

    suspend fun getFactDetails() = apiService?.getFactDetails()
}