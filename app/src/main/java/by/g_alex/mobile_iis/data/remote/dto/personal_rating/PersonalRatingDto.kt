package by.g_alex.mobile_iis.data.remote.dto.personal_rating

data class PersonalRatingDto(
    val fio: String,
    val id: Int,
    val lessons: List<Lesson>,
    val subGroup: Int,
    val subGroupStudent: Int
)