package com.my.first.elearningapp.api

import com.my.first.elearningapp.model.CourseResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("Curso")
    suspend fun getCourses(): Response<List<CourseResponse>>
}