package com.example.chocopop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var nombreInput: EditText
    private lateinit var apellidoInput: EditText
    private lateinit var usuarioInput: EditText
    private lateinit var correoInput: EditText
    private lateinit var contraseñaInput: EditText
    private lateinit var confirmarContraseñaInput: EditText
    private lateinit var registerBtn: Button
    private lateinit var loginPrompt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Vincular los elementos de la interfaz con sus IDs
        nombreInput = findViewById(R.id.register_nombre)
        apellidoInput = findViewById(R.id.register_apellido)
        usuarioInput = findViewById(R.id.register_usuario)
        correoInput = findViewById(R.id.register_correo)
        contraseñaInput = findViewById(R.id.register_contraseña)
        confirmarContraseñaInput = findViewById(R.id.register_confirmar_contraseña)
        registerBtn = findViewById(R.id.register_button)
        loginPrompt = findViewById(R.id.register_login_prompt)

        // Manejar el evento de clic en el botón de registro
        registerBtn.setOnClickListener {
            val nombre = nombreInput.text.toString().trim()
            val apellido = apellidoInput.text.toString().trim()
            val usuario = usuarioInput.text.toString().trim()
            val correo = correoInput.text.toString().trim()
            val contraseña = contraseñaInput.text.toString().trim()
            val confirmarContraseña = confirmarContraseñaInput.text.toString().trim()

            // Validar campos vacíos
            if (nombre.isEmpty() || apellido.isEmpty() || usuario.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_LONG).show()
            }
            // Validar coincidencia de contraseñas
            else if (contraseña != confirmarContraseña) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            }
            // Aquí iría la lógica de registro (puedes agregar una llamada a una API o base de datos)
            else {
                // Por ejemplo, código para registrar usuario en Firebase o base de datos local
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_LONG).show()

                // Redirigir al inicio de sesión después del registro exitoso
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish() // Opcional: cierra la pantalla de registro
            }
        }

        // Manejar clic en "¿Ya tienes una cuenta? Iniciar Sesión"
        loginPrompt.setOnClickListener {
            // Redirigir a la pantalla de inicio de sesión
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Opcional: cierra la pantalla de registro
        }
    }
}
