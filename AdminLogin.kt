package com.example.easemycampus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdminLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adminUserNameEditText = findViewById<EditText>(R.id.adminUserNameEditText)
        val adminPasswordEditText = findViewById<EditText>(R.id.adminPasswordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        // Hardcoded admin username and password
        val adminUsername = "admin"
        val adminPassword = "admin@123"

        loginButton.setOnClickListener {
            val enteredUsername = adminUserNameEditText.text.toString().trim()
            val enteredPassword = adminPasswordEditText.text.toString().trim()

            if (enteredUsername == adminUsername && enteredPassword == adminPassword) {
                // Admin credentials are correct
                Toast.makeText(this, "Admin login successful", Toast.LENGTH_SHORT).show()
                // Navigate to the activity where admin can change the status of leave requests
                startActivity(Intent(this, ApproveOutpass::class.java))
                finish()
            } else {
                // Admin credentials are incorrect
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}