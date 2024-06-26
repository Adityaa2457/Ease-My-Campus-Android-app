package com.example.easemycampus

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ApplyForLeave : AppCompatActivity() {

    private lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_apply_for_leave)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FirebaseDatabase.getInstance().reference

        // Find views
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val registrationNumberEditText = findViewById<EditText>(R.id.registrationNumberEditText)
        val leaveAddressEditText = findViewById<EditText>(R.id.leaveAddressEditText)
        val modeOfTransportEditText = findViewById<EditText>(R.id.modeOfTransportEditText)
        val parentsPhoneNumberEditText = findViewById<EditText>(R.id.parentsPhoneNumberEditText)
        val startDateEditText = findViewById<EditText>(R.id.startDateEditText)
        val endDateEditText = findViewById<EditText>(R.id.endDateEditText)
        val reasonEditText = findViewById<EditText>(R.id.reasonEditText)
        val submitLeaveButton = findViewById<Button>(R.id.submitLeaveButton)

        // Set onClickListener for submit button
        submitLeaveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val registrationNumber = registrationNumberEditText.text.toString().trim()
            val leaveAddress = leaveAddressEditText.text.toString().trim()
            val modeOfTransport = modeOfTransportEditText.text.toString().trim()
            val parentsPhoneNumber = parentsPhoneNumberEditText.text.toString().trim()
            val startDate = startDateEditText.text.toString().trim()
            val endDate = endDateEditText.text.toString().trim()
            val reason = reasonEditText.text.toString().trim()
            val status:String="Pending"

            // Check if any field is empty
            if (name.isEmpty() || registrationNumber.isEmpty() || leaveAddress.isEmpty() ||
                modeOfTransport.isEmpty() || parentsPhoneNumber.isEmpty() ||
                startDate.isEmpty() || endDate.isEmpty() || reason.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a Leave object
            val leave = Leave(name, registrationNumber, leaveAddress, modeOfTransport, parentsPhoneNumber, startDate, endDate, reason,status)

            // Push data to Firebase Database
            database.child("Leaves").push().setValue(leave)
                .addOnSuccessListener {
                    Toast.makeText(this, "Leave application submitted successfully", Toast.LENGTH_SHORT).show()
                    // Clear EditText fields after submission
                    nameEditText.text.clear()
                    registrationNumberEditText.text.clear()
                    leaveAddressEditText.text.clear()
                    modeOfTransportEditText.text.clear()
                    parentsPhoneNumberEditText.text.clear()
                    startDateEditText.text.clear()
                    endDateEditText.text.clear()
                    reasonEditText.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to submit leave application", Toast.LENGTH_SHORT).show()
                }
        }
    }
    data class Leave(
        val name: String = "",
        val registrationNumber: String = "",
        val leaveAddress: String = "",
        val modeOfTransport: String = "",
        val parentsPhoneNumber: String = "",
        val startDate: String = "",
        val endDate: String = "",
        val reason: String = "",
        val status: String = "",
        var leaveId: String? = null
    )
 }
