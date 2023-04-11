package by.g_alex.mobile_iis.data.remote.dto.employee

import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel

data class EmployeeListItemDto(
    val academicDepartment: List<String>,
    val calendarId: String,
    val degree: String,
    val fio: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val middleName: String?,
    val photoLink: String,
    val rank: String,
    val urlId: String
)
fun EmployeeListItemDto.toEmployeeModel(): EmployeeModel {
    return EmployeeModel(
        academicDepartment = academicDepartment,
        degree = degree,
        fio = fio,
        firstName = firstName,
        id = id,
        lastName = lastName,
        middleName = middleName ?: "",
        photoLink = photoLink,
        rank = rank,
        urlId = urlId
    )
}