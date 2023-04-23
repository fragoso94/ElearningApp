package com.my.first.elearningapp.bl.services

import com.my.first.elearningapp.bl.interfaces.IAutentication
import com.my.first.elearningapp.model.SimpleResponse
import com.my.first.elearningapp.model.User

val users = mutableListOf<User>()

class AutenticationServices : IAutentication {

    override fun iniciarSesion(user: String, pass: String): SimpleResponse {
        val response = SimpleResponse()
        users.add(User("daniel","ing.fragoso94@gmail.com", "9612674521", "123456"))
        // Verificamos si el usuario ya existe
        val usuarioExistente = users.find { it.name == user && it.password == pass }
        if (usuarioExistente != null)
        {
            //cargarCursos()
            response.exito = true
            response.mensaje = "Inicio de sesión correctamente"
        }
        else
        {
            response.exito = false
            response.mensaje = "Credenciales Incorrectas"
        }
        return response
    }


}