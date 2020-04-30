package com.example.telstrademo.data.repository

import com.example.telstrademo.data.api.ApiHelper

class MainRepository (private val apiHelper: ApiHelper) {

    suspend fun getFactDetails() = apiHelper.getFactDetails()
}