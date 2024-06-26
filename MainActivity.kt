package com.example.easemycampus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easemycampus.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        val currentUser : FirebaseUser? = auth.currentUser
        if(currentUser != null)
        {
            startActivity(Intent(this,HomeScreen::class.java))
            finish()
        }
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

        auth= FirebaseAuth.getInstance()
        binding.login.setOnClickListener {
            val username =binding.regemail.text.toString()
            val password =binding.password.text.toString()

            if(username.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this, "Please Fill all the details ", Toast.LENGTH_SHORT).show()
            }
            else
            {
                auth.signInWithEmailAndPassword(username,password)
                    .addOnCompleteListener{task->
                        if(task.isSuccessful)
                        {
                            Toast.makeText(this, "Sign in Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,HomeScreen::class.java))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this, "Sign in Failed:${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

        binding.newUser.setOnClickListener {
            val intent2=Intent(this,RegistrationPage::class.java)
            startActivity(intent2)
        }
    }
}


