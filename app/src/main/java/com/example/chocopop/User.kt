package com.example.chocopop

data class User(
    val nombre: String, // Asegúrate de que este campo corresponda al backend
    val email: String,  // Cambiado de "correo" a "email"
    val password: String, // Cambiado de "contraseña" a "password"
    val direccion: String,
    val telefono: String,
    val token: String = "", // Campo por defecto vacío
    val confirmado: Boolean = true // Campo por defecto a true
)
