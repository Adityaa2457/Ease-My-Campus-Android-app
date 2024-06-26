package com.example.easemycampus

import RideRequest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easemycampus.databinding.ActivityRequestRideBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class Request_Ride : AppCompatActivity() {

    private lateinit var binding: ActivityRequestRideBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private var selectedDate: String = ""
    private var selectedTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRequestRideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Firebase database Reference
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        binding.date.setOnClickListener {
            showDatePicker()
        }

        binding.time.setOnClickListener {
            showTimePicker()
        }

        binding.submitRequest.setOnClickListener {

            // Get data
            val name = binding.name.text.toString()
            val from = binding.from.text.toString()
            val destination = binding.destination.text.toString()
            val phone = binding.phone2.text.toString()

            if (name.isEmpty() || from.isEmpty() || destination.isEmpty() || phone.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val currentUser = auth.currentUser
                currentUser?.let { user ->
                    // Generate a unique key for each request
                    val requestKey = databaseReference.child("User").child(user.uid).child("ride requests").push().key

                    // RideRequest instance
                    val rideRequestItem = RideRequest(name, from, destination, phone, selectedDate, selectedTime)
                    requestKey?.let { key ->
                        databaseReference.child("User").child(user.uid).child("ride requests").child(key).setValue(rideRequestItem)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Request submitted successfully", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else {
                                    Toast.makeText(this, "Failed to submit request", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            }
        }

        binding.viewRequest.setOnClickListener {
            startActivity(Intent(this, My_Ride_Requests::class.java))
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, day ->
                selectedDate = "$day/${month + 1}/$year"
                binding.date.setText(selectedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedTime = "$hourOfDay:$minute"
                binding.time.setText(selectedTime)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }
}
