package com.example.chocopop
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class ProductDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val productImage = findViewById<ImageView>(R.id.detail_productImage)
        val productName = findViewById<TextView>(R.id.detail_productName)
        val productPrice = findViewById<TextView>(R.id.detail_productPrice)
        val productDescription = findViewById<TextView>(R.id.detail_productDescription)


        // Obtener el producto del Intent
        val product = intent.getParcelableExtra<Product>("product")

        product?.let {
            productName.text = it.nombre
            productPrice.text = "Precio: BS ${it.precio}"
            productDescription.text = "Descripción: ${it.descripcion}"
           

            Glide.with(this).load(it.imagen).into(productImage)

            // Redirigir a CarritoActivity al hacer click en el botón
            findViewById<Button>(R.id.addToCartButton).setOnClickListener {
                val intent = Intent(this, CarritoActivity::class.java)
                startActivity(intent)  // Redirigir al carrito
            }

        } ?: run {
            Toast.makeText(this, "Producto no disponible", Toast.LENGTH_SHORT).show()
        }
    }
}

