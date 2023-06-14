package com.my.first.elearningapp.model

import com.google.gson.annotations.SerializedName

data class CourseResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("category") val category: String,
    @SerializedName("name") val name: String,
    @SerializedName("duration") val duration: Float,
    @SerializedName("price") val price: Float,
    @SerializedName("rating") val rating: Float,

)