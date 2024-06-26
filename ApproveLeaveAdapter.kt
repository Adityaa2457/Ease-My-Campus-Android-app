import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easemycampus.ApplyForLeave
import com.example.easemycampus.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ApproveLeaveAdapter(private val leaveRequestsList: List<ApplyForLeave.Leave>) :
    RecyclerView.Adapter<ApproveLeaveAdapter.LeaveRequestViewHolder>() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    class LeaveRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val registrationNumberTextView: TextView = itemView.findViewById(R.id.registrationNumberTextView)
        val leaveDatesTextView: TextView = itemView.findViewById(R.id.leaveDatesTextView)
        val leaveAddressTextView: TextView = itemView.findViewById(R.id.leaveAddressTextView)
        val modeOfTransportTextView: TextView = itemView.findViewById(R.id.modeOfTransportTextView)
        val parentsPhoneNumberTextView: TextView = itemView.findViewById(R.id.parentsPhoneNumberTextView)
        val reasonTextView: TextView = itemView.findViewById(R.id.reasonTextView)
        val approveButton: Button = itemView.findViewById(R.id.approveButton)
        val rejectButton: Button = itemView.findViewById(R.id.rejectButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaveRequestViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.approve_request_item, parent, false)

        return LeaveRequestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LeaveRequestViewHolder, position: Int) {
        val currentItem = leaveRequestsList[position]

        holder.nameTextView.text = "Name: ${currentItem.name}"
        holder.registrationNumberTextView.text = "Registration Number: ${currentItem.registrationNumber}"
        holder.leaveDatesTextView.text = "Leave Dates: ${currentItem.startDate} to ${currentItem.endDate}"
        holder.leaveAddressTextView.text = "Leave Address: ${currentItem.leaveAddress}"
        holder.modeOfTransportTextView.text = "Mode of Transport: ${currentItem.modeOfTransport}"
        holder.parentsPhoneNumberTextView.text = "Parent's Phone Number: ${currentItem.parentsPhoneNumber}"
        holder.reasonTextView.text = "Reason: ${currentItem.reason}"

        holder.approveButton.setOnClickListener {
            // Update status to approved
            database.child("Leaves").child(currentItem.leaveId!!).child("status").setValue("Approved")
        }

        holder.rejectButton.setOnClickListener {
            // Update status to rejected
            database.child("Leaves").child(currentItem.leaveId!!).child("status").setValue("Rejected")
        }
    }

    override fun getItemCount() = leaveRequestsList.size
}
