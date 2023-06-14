package com.my.first.elearningapp.api

import com.my.first.elearningapp.database.utilities.Helpers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    val courseService: ApiService =
        Retrofit.Builder()
            .baseUrl(Helpers.URL_BASE_API + "api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}