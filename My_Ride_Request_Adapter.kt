import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easemycampus.R

class RideRequestAdapter(private var rideRequests: List<RideRequest>) : RecyclerView.Adapter<RideRequestAdapter.RideRequestViewHolder>() {

    // Update adapter data
    fun updateData(newRideRequests: List<RideRequest>) {
        rideRequests = newRideRequests
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideRequestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.my_ride_requests_item, parent, false)
        return RideRequestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RideRequestViewHolder, position: Int) {
        val currentRequest = rideRequests[position]
        holder.fromTextView.text = "From: ${currentRequest.from}"
        holder.destinationTextView.text = "Destination: ${currentRequest.destination}"
        holder.dateTextView.text = "Date: ${currentRequest.date}"
        holder.timeTextView.text = "Time: ${currentRequest.time}"
        holder.nameTextView.text ="Name: ${currentRequest.name}"
        holder.phoneTextView.text = "Phone: ${currentRequest.phone}"
    }

    override fun getItemCount() = rideRequests.size

    class RideRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fromTextView:TextView = itemView.findViewById(R.id.fromTextView)
        val destinationTextView: TextView = itemView.findViewById(R.id.destinationTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val timeTextView: TextView = itemView.findViewById(R.id.timeTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val phoneTextView: TextView = itemView.findViewById(R.id.phoneTextView)
    }
}
