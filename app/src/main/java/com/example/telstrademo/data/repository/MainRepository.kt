package com.example.telstrademo.data.repository

import com.example.telstrademo.data.api.ApiHelper

/*In Repository pattern, we will be linking our ApiHelper class by using a Repository class*/
class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getFactDetails() = apiHelper.getFactDetails()
}