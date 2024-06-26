package com.example.easemycampus

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easemycampus.databinding.ActivityOutpassBinding

class Outpass : AppCompatActivity() {
    private val binding:ActivityOutpassBinding by lazy {
        ActivityOutpassBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.applyLeaveButton.setOnClickListener {
            startActivity(Intent(this,ApplyForLeave::class.java))

        }
        binding.viewStatusButton.setOnClickListener {
            startActivity(Intent(this,ViewMyLeaves::class.java))

        }
        binding.adminLoginButton.setOnClickListener {
            startActivity(Intent(this,AdminLogin::class.java))

        }
    }
}