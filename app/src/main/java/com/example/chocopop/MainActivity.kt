package com.example.chocopop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    lateinit var usuarioInput: EditText
    lateinit var contraseñaInput: EditText
    lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usuarioInput=findViewById(R.id.usuario)
        contraseñaInput=findViewById(R.id.contraseña)
        loginBtn=findViewById(R.id.login)

        loginBtn.setOnClickListener {
            val username = usuarioInput.text.toString()
            val password = contraseñaInput.text.toString()

            // Aquí puedes validar los datos si es necesario

            // Crear un Intent para cambiar a la nueva Activity (DashboardActivity)
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }
    }
}