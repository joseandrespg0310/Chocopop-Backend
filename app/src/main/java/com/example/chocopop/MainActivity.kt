package com.example.chocopop

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var usuarioInput: EditText
    private lateinit var contraseñaInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerPrompt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        usuarioInput = findViewById(R.id.login_usuario)
        contraseñaInput = findViewById(R.id.login_contraseña)
        loginBtn = findViewById(R.id.login_iniciar_sesion)
        registerPrompt = findViewById(R.id.login_register_prompt)

        loginBtn.setOnClickListener {
            val username = usuarioInput.text.toString().trim()
            val password = contraseñaInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                iniciarSesion(username, password)
            }
        }

        registerPrompt.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun iniciarSesion(username: String, password: String) {
        val loginRequest = LoginRequest(identificador = username, password = password)

        val apiService = RetrofitClient.instance.loginUser(loginRequest)
        apiService.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null && loginResponse.success) {
                        Toast.makeText(this@MainActivity, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show()

                        // Guardar el estado de sesión y los datos del usuario en SharedPreferences
                        val sharedPref = getSharedPreferences("user_data", Context.MODE_PRIVATE)
                        sharedPref.edit().apply {
                            putString("user_id", loginResponse.userId)
                            putString("user_name", loginResponse.userName)
                            putString("user_phone", loginResponse.userPhone)
                            putString("user_address", loginResponse.userAddress)
                            putBoolean("isLoggedIn", true)
                            apply()
                        }

                        // Redirigir al DashboardActivity
                        val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()  // Finalizar la actividad actual para que no se pueda regresar a ella
                    } else {
                        Toast.makeText(this@MainActivity, loginResponse?.message ?: "Error en el inicio de sesión", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error en el inicio de sesión"
                    Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
