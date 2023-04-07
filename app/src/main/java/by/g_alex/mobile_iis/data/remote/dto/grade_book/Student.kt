package by.g_alex.mobile_iis.data.remote.dto.grade_book

data class Student(
    val fio: String,
    val id: Int,
    val lessons: List<Lesson>,
    val subGroup: Int,
    val subGroupStudent: Int
)