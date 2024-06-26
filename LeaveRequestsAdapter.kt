import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easemycampus.ApplyForLeave
import com.example.easemycampus.R

class LeaveRequestsAdapter(var leaveRequestsList: List<ApplyForLeave.Leave>) :
    RecyclerView.Adapter<LeaveRequestsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        var registrationNumberTextView: TextView = itemView.findViewById(R.id.registrationNumberTextView)
        var leaveAddressTextView: TextView = itemView.findViewById(R.id.leaveAddressTextView)
        var modeOfTransportTextView: TextView = itemView.findViewById(R.id.modeOfTransportTextView)
        var parentsPhoneNumberTextView: TextView = itemView.findViewById(R.id.parentsPhoneNumberTextView)
        var startDateTextView: TextView = itemView.findViewById(R.id.startDateTextView)
        var endDateTextView: TextView = itemView.findViewById(R.id.endDateTextView)
        var reasonTextView: TextView = itemView.findViewById(R.id.reasonTextView)
        var statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.leave_request_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = leaveRequestsList[position]
        holder.nameTextView.text = "Name: ${currentItem.name}"
        holder.registrationNumberTextView.text = "Registration Number: ${currentItem.registrationNumber}"
        holder.leaveAddressTextView.text = "Leave Address: ${currentItem.leaveAddress}"
        holder.modeOfTransportTextView.text = "Mode Of Transport: ${currentItem.modeOfTransport}"
        holder.parentsPhoneNumberTextView.text = "Parent's Phone Number: ${currentItem.parentsPhoneNumber}"
        holder.startDateTextView.text = "Start Date: ${currentItem.startDate}"
        holder.endDateTextView.text = "End Date: ${currentItem.endDate}"
        holder.reasonTextView.text = "Reason for leave: ${currentItem.reason}"
        holder.statusTextView.text = "Status: ${currentItem.status}"
    }

    override fun getItemCount(): Int {
        return leaveRequestsList.size
    }
}
