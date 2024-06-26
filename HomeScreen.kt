package com.example.easemycampus
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class HomeScreen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth= FirebaseAuth.getInstance()
        val btn_logout=findViewById<Button>(R.id.logout)
        btn_logout.setOnClickListener {
            auth.signOut()
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val btn_carpool=findViewById<ImageButton>(R.id.carpool)
        btn_carpool.setOnClickListener {
            val intent=Intent(this,Carpool::class.java)
            startActivity(intent)
        }
        val btn_outpass=findViewById<ImageButton>(R.id.outpass)
        btn_outpass.setOnClickListener {
            val intent=Intent(this,Outpass::class.java)
            startActivity(intent)
        }
        val btn_food=findViewById<ImageButton>(R.id.food)
        btn_food.setOnClickListener {
            val intent=Intent(this,OrderFood::class.java)
            startActivity(intent)
        }
    }
}