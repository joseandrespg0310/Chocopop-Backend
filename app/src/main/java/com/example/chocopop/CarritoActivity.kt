package com.example.chocopop

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class CarritoActivity : AppCompatActivity() {

    private var productList: MutableList<Product> = mutableListOf()
    private lateinit var subtotalTextView: TextView
    private lateinit var totalTextView: TextView
    private lateinit var productContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        subtotalTextView = findViewById(R.id.subtotal)
        totalTextView = findViewById(R.id.total)
        productContainer = findViewById(R.id.productContainer)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Obtener los productos del carrito (presumiblemente desde CartManager)
        getProducts()

        val buyButton: Button = findViewById(R.id.btn_comprar)
        buyButton.setOnClickListener {
            // Crear el pedido y redirigir al pago
            createOrder()
            val intent = Intent(this, PagoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getProducts() {
        val call = RetrofitClient.instance.getProducts()
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val products = response.body()
                    if (products != null && products.isNotEmpty()) {
                        productList = products.toMutableList()
                        displayProducts()
                    } else {
                        Toast.makeText(this@CarritoActivity, "No hay productos disponibles.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("API_ERROR", "Error en la respuesta: ${response.errorBody()?.string()}")
                    Toast.makeText(this@CarritoActivity, "Error al obtener productos.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Log.e("API_FAILURE", "Error en la llamada a la API", t)
                Toast.makeText(this@CarritoActivity, "Error de conexión.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun displayProducts() {
        productContainer.removeAllViews() // Limpiar vistas previas

        for (product in productList) {
            val productView = LayoutInflater.from(this).inflate(R.layout.item_product, productContainer, false)

            val productName = productView.findViewById<TextView>(R.id.productName)
            val productPrice = productView.findViewById<TextView>(R.id.productPrice)
            val productImage = productView.findViewById<ImageView>(R.id.productImage)
            val decreaseButton = productView.findViewById<Button>(R.id.decreaseButton)
            val increaseButton = productView.findViewById<Button>(R.id.increaseButton)
            val quantityText = productView.findViewById<TextView>(R.id.productQuantity)

            productName.text = product.nombre
            productPrice.text = "Precio: BS ${product.precio}"
            quantityText.text = product.cantidad.toString()

            Glide.with(this).load(product.imagen).into(productImage)

            decreaseButton.setOnClickListener {
                if (product.cantidad > 0) {  // Evitar que la cantidad sea menor que 0
                    product.cantidad--
                    quantityText.text = product.cantidad.toString()
                    updateTotal()
                }
            }

            increaseButton.setOnClickListener {
                if (product.cantidad < 10) {  // Limitar la cantidad a un máximo de 10
                    product.cantidad++
                    quantityText.text = product.cantidad.toString()
                    updateTotal()
                }
            }

            productContainer.addView(productView)
        }

        updateTotal()
    }


    private fun updateTotal() {
        if (productList.isEmpty()) return

        var subtotal = 0.0
        productList.forEach {
            subtotal += it.precio * it.cantidad
        }
        subtotalTextView.text = "Sub Total: BS ${"%.2f".format(subtotal)}"
        val discount = 0.1
        val total = subtotal * (1 - discount)
        totalTextView.text = "TOTAL: BS ${"%.2f".format(total)}"
    }

    // Crear el pedido en el backend
    private fun createOrder() {
        val userId = getUserIdFromPreferences()  // Obtener el ID del usuario desde tu sistema de autenticación (SharedPreferences o token)
        val userName = "Sergio Campos"  // Obtener el nombre del usuario
        val userPhone = "76591151"  // Obtener el teléfono del usuario
        val address = "Obrajes calle 17"  // Obtener la dirección de entrega

        if (productList.isEmpty()) {
            Toast.makeText(this, "No hay productos en el carrito.", Toast.LENGTH_SHORT).show()
            return
        }

        val order = Order(
            usuario = userId, // userId debería ser un String que es el _id del usuario
            productos = productList.map {
                ProductOrder(
                    productoId = it._id,  // Asegúrate de que _id existe
                    nombre = it.nombre,  // Asegúrate de que 'nombre' exista en tu modelo de 'Product'
                    precio = it.precio,  // Asegúrate de que 'precio' esté en el modelo 'Product'
                    cantidad = it.cantidad,  // Asegúrate de que 'cantidad' esté definido
                    subtotal = it.precio * it.cantidad
                )
            },
            total = productList.sumOf { it.precio * it.cantidad },
            fecha = getCurrentDate(),
            estado = "Pendiente",
            direccionDestinatario = address,
            nombreDestinatario = userName,
            telefonoDestinatario = userPhone
        )

        val call = RetrofitClient.instance.createOrder(order)
        call.enqueue(object : Callback<OrderResponse> {
            override fun onResponse(call: Call<OrderResponse>, response: Response<OrderResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CarritoActivity, "Pedido realizado con éxito", Toast.LENGTH_SHORT).show()
                    // Redirigir a la siguiente actividad (por ejemplo, PagoActivity)
                    val intent = Intent(this@CarritoActivity, PagoActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@CarritoActivity, "Pagar Pedido", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OrderResponse>, t: Throwable) {
                Toast.makeText(this@CarritoActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Obtener la fecha actual
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    // Obtener el ID del usuario desde SharedPreferences o almacenamiento local
    private fun getUserIdFromPreferences(): String {
        // Aquí deberías tener una forma de obtener el ID del usuario (por ejemplo, desde SharedPreferences o algún sistema de token)
        return "id_del_usuario"
    }
}
