package com.example.chocopop

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)  // Asegúrate de que este es el layout correcto
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT



        // Encuentra el botón "Ver Catálogo"
        val catalogoButton: Button = findViewById(R.id.btn_ver_catalogo)
        val catalogoButton1: Button = findViewById(R.id.button_grid)

        // Encuentra el ícono de carrito
        val carritoButton: Button = findViewById(R.id.btn_carrito)
        val carritoButton1: Button = findViewById(R.id.button_phone)

        // Encuentra el ícono de "Nosotros"
        val nosotrosButton: Button = findViewById(R.id.button_info)  // Asegúrate de que el id sea correcto



        // Configura el clic del botón Ver Catálogo
        catalogoButton.setOnClickListener {
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
        }

        // Configura el clic del botón Ver Catálogo
        catalogoButton1.setOnClickListener {
            val intent = Intent(this, CatalogoActivity::class.java)
            startActivity(intent)
        }


// Configura el clic del ícono del carrito
        carritoButton.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }
        carritoButton1.setOnClickListener {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        }

        nosotrosButton.setOnClickListener {
            val intent = Intent(this, NosotrosActivity::class.java)  // Redirige a NosotrosActivity
            startActivity(intent)
        }
    }

    // Método para verificar si el usuario está autenticado
    private fun isUserLoggedIn(): Boolean {
        // Verificamos en SharedPreferences si el usuario está logueado
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false) // Retorna si el usuario está logueado
    }

    // Método para cerrar sesión
    private fun logout() {
        // Limpiar el estado de sesión
        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        sharedPref.edit().putBoolean("isLoggedIn", false).apply()

        // Redirigir a la pantalla de inicio de sesión
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Finalizar la actividad actual para que no se pueda regresar a ella
    }
}
