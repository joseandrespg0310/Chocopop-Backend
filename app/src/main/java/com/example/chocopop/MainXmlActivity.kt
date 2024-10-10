package com.example.chocopop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainXmlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main) // Asegúrate de que este es el nombre correcto del layout principal

        // Obtener el botón por su ID
        val loginButton: Button = findViewById(R.id.loginButton)

        // Asignar un listener al botón
        loginButton.setOnClickListener {
            // Crear un Intent para iniciar la actividad de login (MainActivity)
            val intent = Intent(this, MainActivity::class.java)

            // Iniciar la actividad de login
            startActivity(intent)
        }
    }
}
