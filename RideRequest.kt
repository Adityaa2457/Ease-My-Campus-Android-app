data class RideRequest(
    val name: String? = null,
    val from: String? = null,
    val destination: String? = null,
    val phone: String? = null,
    val date: String? = null,
    val time: String? = null
) {
    // Add a no-argument constructor
    constructor() : this("", "", "", "", "", "")
}


