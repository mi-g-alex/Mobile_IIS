package by.g_alex.mobile_iis.data.remote.dto.students

data class StudentResponceDto(
    val course: List<Any>,
    val currentPage: Int,
    val cvs: List<Cv>,
    val faculties: List<Any>,
    val lastName: String,
    val pageSize: Int,
    val searchJob: Boolean,
    val skills: List<Any>,
    val totalItems: Int,
    val totalPages: Int
)