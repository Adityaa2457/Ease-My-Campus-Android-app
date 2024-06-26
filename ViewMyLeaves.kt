package com.example.easemycampus

import LeaveRequestsAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.easemycampus.databinding.ActivityViewMyLeavesBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ViewMyLeaves : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var leaveRequestsAdapter: LeaveRequestsAdapter
    private val leaveRequestsList = mutableListOf<ApplyForLeave.Leave>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_my_leaves)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        database = FirebaseDatabase.getInstance().reference

        recyclerView = findViewById(R.id.leaveRequestsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        leaveRequestsAdapter = LeaveRequestsAdapter(leaveRequestsList)
        recyclerView.adapter = leaveRequestsAdapter

        fetchLeaveRequests()
    }

    private fun fetchLeaveRequests() {
        val currentUser = auth.currentUser
        val query = database.child("Leaves")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                leaveRequestsList.clear()
                for (leaveSnapshot in snapshot.children) {
                    val leave = leaveSnapshot.getValue(ApplyForLeave.Leave::class.java)
                    leave?.let {
                        // Filter leave requests for current user
                        leaveRequestsList.add(it)
                    }
                }
                leaveRequestsAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@ViewMyLeaves,
                    "Failed to fetch leave requests",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}