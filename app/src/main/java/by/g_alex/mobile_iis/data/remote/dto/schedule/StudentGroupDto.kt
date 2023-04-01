package by.g_alex.mobile_iis.data.remote.dto.schedule

data class StudentGroupDto(
    val name: String,
    val facultyId: Int,
    val facultyName: String,
    val specialityDepartmentEducationFormId: Int,
    val specialityName: String,
    val specialityAbbrev: String,
    val course: Int,
    val id : Int,
    val calendarId: String,
    val educationDegree: Int
)