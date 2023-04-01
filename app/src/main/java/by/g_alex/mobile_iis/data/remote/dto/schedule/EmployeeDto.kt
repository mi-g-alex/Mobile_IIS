package by.g_alex.mobile_iis.data.remote.dto.schedule

data class EmployeeDto(
    val id: Int,
    val firstName: String,
    val middleName: String,
    val lastName: String,
    val photoLink: String?,
    val degree: String?,
    val degreeAbbrev: String,
    val rank: String?,
    val email: String?,
    val department: String?,
    val urlId: String,
    val calendarId: String?,
)