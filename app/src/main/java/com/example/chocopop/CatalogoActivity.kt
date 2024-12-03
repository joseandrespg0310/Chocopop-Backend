package com.example.chocopop

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogoActivity : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CatalogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalogo)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        recyclerView = findViewById(R.id.catalogRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        fetchProducts()
    }

    private fun fetchProducts() {
        val call = RetrofitClient.instance.getProducts() // Usa tu API
        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val products = response.body() ?: emptyList()
                    adapter = CatalogAdapter(this@CatalogoActivity, products)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@CatalogoActivity, "Error al cargar productos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(this@CatalogoActivity, "Fallo de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
