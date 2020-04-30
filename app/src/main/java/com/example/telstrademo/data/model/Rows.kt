package com.example.telstrademo.data.model

import com.google.gson.annotations.SerializedName

data class Rows (

    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("imageHref") val imageHref : String
)
