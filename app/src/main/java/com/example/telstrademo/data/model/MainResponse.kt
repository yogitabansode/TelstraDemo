package com.example.telstrademo.data.model

import com.google.gson.annotations.SerializedName

data class MainResponse(

    @SerializedName("title") val title: String,
    @SerializedName("rows") val rows: List<Rows>
)
