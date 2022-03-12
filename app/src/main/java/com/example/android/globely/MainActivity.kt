package com.example.android.globely

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val MapsActivity = MapsActivity()
        val btnPlay: Button = findViewById(R.id.btnPlay)
        val switchEasyMode: SwitchCompat = findViewById(R.id.switchEasyMode)

        btnPlay.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("easy mode", switchEasyMode.isChecked)
            startActivity(intent)
        }
    }
}