package com.example.chocopop

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/register") // Ruta para registrar un usuario
    fun registerUser(@Body user: User): Call<ResponseBody>

    @POST("auth/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>
    // Obtener productos
    @GET("products")
    fun getProducts(): Call<List<Product>>

    // Crear un pedido
    @POST("pedidos")
    fun createOrder(@Body order: Order): Call<OrderResponse>
}
