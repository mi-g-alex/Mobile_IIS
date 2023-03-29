package by.g_alex.mobile_iis.domain.model.profile.schedule

data class EmployeeModel(
    val academicDepartment: List<String>,
    val degree: String,
    val fio: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val middleName: String,
    val photoLink: String,
    val rank: String?,
    val urlId: String
)
