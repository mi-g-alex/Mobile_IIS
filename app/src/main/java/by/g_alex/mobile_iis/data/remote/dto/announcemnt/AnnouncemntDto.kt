package by.g_alex.mobile_iis.data.remote.dto.announcemnt

data class AnnouncemntDto(
    val auditory: String?,
    val content: String?,
    val date: String?,
    val employee: String?,
    val employeeDepartments: List<String>?,
    val endTime: String?,
    val id: Int?,
    val startTime: String?,
    val studentGroups: List<StudentGroup>?,
    val urlId: String?
)