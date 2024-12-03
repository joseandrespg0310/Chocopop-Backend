package com.example.chocopop

data class ProductOrder(
    val productoId: String,  // ID del producto
    val nombre: String,      // Nombre del producto
    val precio: Double,      // Precio del producto
    val cantidad: Int,       // Cantidad del producto
    val subtotal: Double     // Subtotal calculado
)

data class Order(
    val usuario: String,                      // ID del usuario que realizó el pedido
    val productos: List<ProductOrder>,        // Lista de productos en el pedido
    val total: Double,                        // Total del pedido
    val fecha: String,                        // Fecha en que se realiza el pedido
    val estado: String = "Pendiente",         // Estado del pedido, por defecto "Pendiente"
    val direccionDestinatario: String,        // Dirección de entrega
    val nombreDestinatario: String,           // Nombre del destinatario
    val telefonoDestinatario: String          // Teléfono del destinatario
)

data class OrderResponse(
    val success: Boolean,
    val message: String
)

