package by.g_alex.mobile_iis.data.remote.dto.dormitory

data class DormitoryDto(
    val acceptedDate: String,
    val applicationDate: String,
    val docContent: String,
    val docReference: String,
    val id: Int,
    val number: Int,
    val numberInQueue: Int,
    val rejectionReason: String,
    val roomInfo: String,
    val settledDate: String,
    val status: String
)