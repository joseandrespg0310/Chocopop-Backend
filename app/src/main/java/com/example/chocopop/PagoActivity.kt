package com.example.chocopop

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PagoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pago)  // Vincula el archivo de diseño "pago.xml"
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Aquí puedes agregar cualquier lógica adicional si es necesario
    }
}
