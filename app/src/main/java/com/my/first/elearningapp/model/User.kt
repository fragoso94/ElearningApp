package com.my.first.elearningapp.model

data class User(
    val name: String,
    val email: String,
    var mobileNumber: String,
    var password: String,
    var cursos: MutableList<Course> = mutableListOf()
)