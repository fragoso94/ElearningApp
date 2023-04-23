package com.my.first.elearningapp.bl.interfaces

import com.my.first.elearningapp.model.SimpleResponse

interface IAutentication {
    fun iniciarSesion(user: String, pass: String): SimpleResponse
}