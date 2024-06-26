package com.example.easemycampus

import ApproveLeaveAdapter
import LeaveRequestsAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easemycampus.databinding.ActivityApproveOutpassBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ApproveOutpass : AppCompatActivity() {

    private lateinit var binding: ActivityApproveOutpassBinding
    private lateinit var database: DatabaseReference
    private lateinit var adapter: ApproveLeaveAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_approve_outpass)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        database = FirebaseDatabase.getInstance().reference

        val leaveRequestsList = mutableListOf<ApplyForLeave.Leave>()
        adapter = ApproveLeaveAdapter(leaveRequestsList)

        val recyclerView = findViewById<RecyclerView>(R.id.leaveRequestsRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Read leave requests from Firebase
        database.child("Leaves").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (leaveSnapshot in snapshot.children) {
                    val leave = leaveSnapshot.getValue(ApplyForLeave.Leave::class.java)
                    if (leave != null) {
                        leave.leaveId = leaveSnapshot.key // Assigning leaveId from Firebase key
                        leaveRequestsList.add(leave)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}