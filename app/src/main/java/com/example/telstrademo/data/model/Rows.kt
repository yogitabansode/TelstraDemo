package com.example.telstrademo.data.model

import com.google.gson.annotations.SerializedName
/* Data class contains POJO elements of DATA fetched from API*/
data class Rows (

    @SerializedName("title") val title : String,
    @SerializedName("description") val description : String,
    @SerializedName("imageHref") val imageHref : String
)
