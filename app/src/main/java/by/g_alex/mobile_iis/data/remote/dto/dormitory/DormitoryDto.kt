package by.g_alex.mobile_iis.data.remote.dto.dormitory

data class DormitoryDto(
    val acceptedDate: Long?,
    val applicationDate: Long?,
    val docContent: String?,
    val docReference: String?,
    val id: Int?,
    val number: Int?,
    val numberInQueue: Int?,
    val rejectionReason: String?,
    val roomInfo: String?,
    val settledDate: Long?,
    val status: String?
)