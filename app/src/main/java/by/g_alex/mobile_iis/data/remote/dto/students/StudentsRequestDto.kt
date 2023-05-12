package by.g_alex.mobile_iis.data.remote.dto.students

data class StudentsRequestDto(
    var course: List<Any>,
    var currentPage: Int,
    var faculties: List<Int>,
    var lastName: String,
    var searchJob: Boolean,
    val skills: List<Any>
)