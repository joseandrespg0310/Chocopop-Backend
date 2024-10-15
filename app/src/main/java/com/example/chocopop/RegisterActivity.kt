package com.example.chocopop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
            } else if (contraseña != confirmarContraseña) {
                // Validar coincidencia de contraseñas
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
            } else {
                // Crear un objeto de usuario
                val user = User(nombre, apellido, usuario, correo, contraseña)

                // Inicializar Retrofit utilizando RetrofitClient
                val apiService = RetrofitClient.instance // Usamos RetrofitClient.instance aquí

                // Llamar a la API
                apiService.registerUser(user).enqueue(object : Callback<ResponseBody> { // Cambiado a ResponseBody
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) { // Cambiado a ResponseBody
                        if (response.isSuccessful) {
                            Toast.makeText(this@RegisterActivity, "Usuario registrado con éxito", Toast.LENGTH_LONG).show()

                            // Redirigir al inicio de sesión después del registro exitoso
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish() // Opcional: cierra la pantalla de registro
                        } else {
                            Toast.makeText(this@RegisterActivity, "Error en el registro", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
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
