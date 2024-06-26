package com.example.easemycampus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easemycampus.databinding.ActivityRegistrationPageBinding
import com.google.firebase.auth.FirebaseAuth

class RegistrationPage : AppCompatActivity() {
    private val binding : ActivityRegistrationPageBinding by lazy {
        ActivityRegistrationPageBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth

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

        binding.register2.setOnClickListener {
            val email=binding.email.text.toString()
            val password=binding.password.text.toString()
            val confirmpass=binding.confirmpass.text.toString()
            val phno=binding.phone.text.toString()

            if(email.isEmpty() || password.isEmpty()||confirmpass.isEmpty()|| phno.isEmpty())
            {
                Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
            }
            else if(password != confirmpass)
            {
                Toast.makeText(this, "Password not matching ", Toast.LENGTH_SHORT).show()
            }
            else
            {
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Registration Successful ", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        else
                        {
                            Toast.makeText(this, "Registration Failed : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }
    }
}