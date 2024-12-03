package com.example.chocopop

// Esta clase se encargará de manejar el carrito de compras
object CartManager {

    // Lista mutable que almacenará los productos en el carrito
    private val cartItems: MutableList<Product> = mutableListOf()

    // Método para agregar un producto al carrito
    fun addProductToCart(product: Product) {
        // Verificamos si el producto ya existe en el carrito
        val existingProduct = cartItems.find { it._id == product._id }
        if (existingProduct != null) {
            // Si el producto ya está en el carrito, incrementamos la cantidad
            existingProduct.cantidad++
        } else {
            // Si no está, lo añadimos al carrito con cantidad 1
            cartItems.add(product.copy(cantidad = 1))
        }
    }

    // Método para obtener todos los productos en el carrito
    fun getCartItems(): List<Product> {
        return cartItems
    }

    // Método para calcular el total de la compra
    fun getTotal(): Double {
        return cartItems.sumOf { it.precio * it.cantidad }
    }


    // Método para limpiar el carrito
    fun clearCart() {
        cartItems.clear()
    }
}
