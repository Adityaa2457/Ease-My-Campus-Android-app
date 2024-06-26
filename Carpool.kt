package com.example.easemycampus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Carpool : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carpool)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val findRideButton = findViewById<Button>(R.id.find_ride)
        findRideButton.setOnClickListener {
            val intent = Intent(this, find_ride::class.java)
            startActivity(intent)
        }

        val requestRideButton = findViewById<Button>(R.id.request_ride)
        requestRideButton.setOnClickListener {
            val intent = Intent(this, Request_Ride::class.java)
            startActivity(intent)
        }
    }
}
