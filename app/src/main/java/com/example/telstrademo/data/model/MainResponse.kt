package com.example.telstrademo.data.model

import com.google.gson.annotations.SerializedName

/*Data class contains POJO elements of DATA fetched from API*/
data class MainResponse(

    @SerializedName("title") val title: String,
    @SerializedName("rows") val rows: List<Rows>
)
