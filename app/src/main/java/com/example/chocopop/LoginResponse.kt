package com.example.chocopop

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val userId: String,
    val userName: String,
    val userPhone: String,
    val userAddress: String
)
