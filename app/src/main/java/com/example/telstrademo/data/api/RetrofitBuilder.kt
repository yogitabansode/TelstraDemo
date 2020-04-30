package com.example.telstrademo.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* create retrofit object by using Retrofit.Builder() and configured BASE_URL */
object RetrofitBuilder {

    private const val BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/"

        private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
}