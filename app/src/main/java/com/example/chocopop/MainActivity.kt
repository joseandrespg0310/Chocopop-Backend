package com.example.chocopop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var usuarioInput: EditText
    private lateinit var contraseñaInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerPrompt: TextView // TextView para redirigir al registro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincular los elementos de la interfaz con sus IDs
        usuarioInput = findViewById(R.id.login_usuario)
        contraseñaInput = findViewById(R.id.login_contraseña)
        loginBtn = findViewById(R.id.login_iniciar_sesion)
        registerPrompt = findViewById(R.id.login_register_prompt) // TextView para redirigir al registro

        // Manejar el evento de clic en el botón de login
        loginBtn.setOnClickListener {
            val username = usuarioInput.text.toString()
            val password = contraseñaInput.text.toString()

            // Validación de los datos ingresados
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Aquí podrías validar el usuario y la contraseña

                // Cambiar a una nueva Activity (DashboardActivity)
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Manejar el clic en el TextView de "¿No tienes una cuenta?"
        registerPrompt.setOnClickListener {
            // Redirigir a la actividad de registro
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
