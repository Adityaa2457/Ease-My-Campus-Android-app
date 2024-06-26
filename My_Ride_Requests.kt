package com.example.easemycampus

import RideRequest
import RideRequestAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easemycampus.databinding.ActivityMyRideRequestsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class My_Ride_Requests : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var rideRequestAdapter: RideRequestAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMyRideRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        val rideRequestsRecyclerView = findViewById<RecyclerView>(R.id.my_ride_requests_recyclerview)
        rideRequestsRecyclerView.layoutManager = LinearLayoutManager(this)
        rideRequestAdapter = RideRequestAdapter(emptyList())
        rideRequestsRecyclerView.adapter = rideRequestAdapter

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Log.d("FirebaseData", "Before fetching data")

        val currentUser = auth.currentUser
        currentUser?.let { user ->
            database.child("User").child(user.uid).child("ride requests").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val rideRequests = mutableListOf<RideRequest>()
                    for (rideRequestSnapshot in snapshot.children) {
                        val rideRequest = rideRequestSnapshot.getValue(RideRequest::class.java)
                        rideRequest?.let {
                            rideRequests.add(it)
                        }
                    }
                    Log.d("FirebaseData", "Ride Requests: $rideRequests")
                    rideRequestAdapter.updateData(rideRequests)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("FirebaseError", "Failed to read value.", error.toException())
                }
            })
        } ?: run {
            Log.e("FirebaseData", "Current user is null")
        }
    }
}
