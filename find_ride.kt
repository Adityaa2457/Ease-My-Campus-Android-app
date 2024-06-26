package com.example.easemycampus

import RideRequest
import RideRequestAdapter
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.Locale

class find_ride : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var rideRequestAdapter: RideRequestAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_find_ride)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        val rideRequestsRecyclerView = findViewById<RecyclerView>(R.id.rideRequestsRecyclerView)
        rideRequestsRecyclerView.layoutManager = LinearLayoutManager(this)
        rideRequestAdapter = RideRequestAdapter(emptyList())
        rideRequestsRecyclerView.adapter = rideRequestAdapter

        // Retrieve data from Firebase
        database.child("User").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val rideRequests = mutableListOf<RideRequest>()
                for (userSnapshot in snapshot.children) {
                    for (rideRequestSnapshot in userSnapshot.child("ride requests").children) {
                        val rideRequest = rideRequestSnapshot.getValue(RideRequest::class.java)
                        rideRequest?.let {
                            // Check if the request time has not passed
                            if (!isTimePassed(rideRequest.date, rideRequest.time)) {
                                rideRequests.add(it)
                            } else {
                                // Remove the request if the time has passed
                                rideRequestSnapshot.ref.removeValue()
                            }
                        }
                    }
                }
                rideRequestAdapter.updateData(rideRequests)
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun isTimePassed(date: String?, time: String?): Boolean {
        // Implement logic to check if the time has passed
        val currentTime = System.currentTimeMillis()
        val dateTime = "$date $time"
        val requestTime = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(dateTime)?.time ?: 0
        return currentTime > requestTime
    }
}
