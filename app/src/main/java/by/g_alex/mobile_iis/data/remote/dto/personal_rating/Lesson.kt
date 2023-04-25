package by.g_alex.mobile_iis.data.remote.dto.personal_rating

data class Lesson(
    val controlPoint: String,
    val dateString: String,
    val gradeBookOmissions: Int,
    val id: Int,
    val lessonNameAbbrev: String,
    val lessonTypeAbbrev: String,
    val lessonTypeId: Int,
    val marks: List<Int>,
    val subGroup: Int
)