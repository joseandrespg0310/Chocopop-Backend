package com.example.chocopop

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // Simular el tiempo de espera (p.ej. 2 segundos)
        Handler(Looper.getMainLooper()).postDelayed({
            // Después del Splash, redirige al Login o Dashboard
            val intent = Intent(this, MainActivity::class.java) // O DashboardActivity
            startActivity(intent)
            finish()  // Para que no se pueda volver a SplashActivity
        }, 2000)  // Tiempo de espera en milisegundos
    }
}

