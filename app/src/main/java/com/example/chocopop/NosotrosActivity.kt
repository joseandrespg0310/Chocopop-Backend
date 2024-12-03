package com.example.chocopop

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class NosotrosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nosotros)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT



    }
}
