package com.example.chocopop

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Patterns
import okhttp3.ResponseBody

class RegisterActivity : AppCompatActivity() {
    private lateinit var nombreInput: EditText
    private lateinit var correoInput: EditText
    private lateinit var contraseñaInput: EditText
    private lateinit var telefonoInput: EditText
    private lateinit var direccionInput: EditText
    private lateinit var registerButton: Button

    private lateinit var errorNombre: TextView
    private lateinit var errorCorreo: TextView
    private lateinit var errorContraseña: TextView
    private lateinit var errorTelefono: TextView
    private lateinit var errorDireccion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        nombreInput = findViewById(R.id.register_nombre)
        correoInput = findViewById(R.id.register_correo)
        contraseñaInput = findViewById(R.id.register_contraseña)
        telefonoInput = findViewById(R.id.register_telefono)
        direccionInput = findViewById(R.id.register_direccion)
        registerButton = findViewById(R.id.register_button)

        errorNombre = findViewById(R.id.error_nombre)
        errorCorreo = findViewById(R.id.error_correo)
        errorContraseña = findViewById(R.id.error_contraseña)
        errorTelefono = findViewById(R.id.error_telefono)
        errorDireccion = findViewById(R.id.error_direccion)

        registerButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        var isValid = true

        errorNombre.visibility = View.GONE
        errorCorreo.visibility = View.GONE
        errorContraseña.visibility = View.GONE
        errorTelefono.visibility = View.GONE
        errorDireccion.visibility = View.GONE

        val nombre = nombreInput.text.toString().trim()
        val correo = correoInput.text.toString().trim()
        val contraseña = contraseñaInput.text.toString().trim()
        val telefono = telefonoInput.text.toString().trim()
        val direccion = direccionInput.text.toString().trim()

        // Validación del campo nombre
        if (nombre.isEmpty()) {
            errorNombre.text = "El nombre es requerido."
            errorNombre.visibility = View.VISIBLE
            isValid = false
        } else if (nombre.any { it.isDigit() }) { // Prohibir números en el nombre
            errorNombre.text = "El nombre no debe contener números."
            errorNombre.visibility = View.VISIBLE
            isValid = false
        } else if (nombre.length > 50) { // Limitar el nombre a 50 caracteres
            errorNombre.text = "El nombre no puede tener más de 50 letras."
            errorNombre.visibility = View.VISIBLE
            isValid = false
        }


        // Validación del correo
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            errorCorreo.text = "El correo no es válido."
            errorCorreo.visibility = View.VISIBLE
            isValid = false
        }

        // Validación de la contraseña
        if (contraseña.length < 8 || !contraseña.any { it.isDigit() } || !contraseña.any { it.isLetter() }) {
            errorContraseña.text = "La contraseña debe tener al menos 8 caracteres y contener letras y números."
            errorContraseña.visibility = View.VISIBLE
            isValid = false
        }

        // Validación del teléfono
        if (!telefono.matches("\\d{7,15}".toRegex())) {
            errorTelefono.text = "El teléfono debe contener solo números y tener entre 7 y 15 dígitos."
            errorTelefono.visibility = View.VISIBLE
            isValid = false
        }

        // Validación de la dirección
        if (direccion.isEmpty() || direccion.matches("\\d+".toRegex())) {
            errorDireccion.text = "La dirección no puede estar vacía o contener solo números."
            errorDireccion.visibility = View.VISIBLE
            isValid = false
        }

        if (isValid) {
            registerUser(nombre, correo, contraseña, telefono, direccion)
        }
    }

    private fun registerUser(nombre: String, correo: String, contraseña: String, telefono: String, direccion: String) {
        val user = User(email = correo, nombre = nombre, password = contraseña, direccion = direccion, telefono = telefono)

        val apiService = RetrofitClient.instance.registerUser(user)

        apiService.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Error al registrar el usuario"
                    Toast.makeText(this@RegisterActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
