package com.example.chocopop

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainXmlActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var identificadorEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var rememberUserCheckBox: CheckBox
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var registerPromptTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializar vistas desde el XML
        loginButton = findViewById(R.id.login_iniciar_sesion)
        identificadorEditText = findViewById(R.id.login_usuario)
        passwordEditText = findViewById(R.id.login_contraseña)

        registerPromptTextView = findViewById(R.id.login_register_prompt)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Configurar evento de clic en el botón de iniciar sesión
        loginButton.setOnClickListener {
            val identificador = identificadorEditText.text.toString().trim() // Cambiar a identificador
            val password = passwordEditText.text.toString().trim()

            if (identificador.isNotEmpty() && password.isNotEmpty()) {
                iniciarSesion(identificador, password) // Cambiar a identificador
            } else {
                Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar evento de clic en el texto de registro
        registerPromptTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Configurar evento de clic en el texto de olvidar contraseña
        forgotPasswordTextView.setOnClickListener {
            Toast.makeText(this, "Función de recuperación de contraseña", Toast.LENGTH_SHORT).show()
        }
    }

    private fun iniciarSesion(identificador: String, password: String) { // Cambiar a identificador
        val loginRequest = LoginRequest(identificador = identificador, password = password) // Cambiar a identificador

        // Llamar a la API para iniciar sesión
        val apiService = RetrofitClient.instance.loginUser(loginRequest)
        apiService.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.success) {
                        Toast.makeText(this@MainXmlActivity, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainXmlActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@MainXmlActivity, loginResponse?.message ?: "Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error en el inicio de sesión"
                    Toast.makeText(this@MainXmlActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainXmlActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
