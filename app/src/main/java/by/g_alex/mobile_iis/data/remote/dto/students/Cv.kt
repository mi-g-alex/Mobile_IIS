package by.g_alex.mobile_iis.data.remote.dto.students

data class Cv(
    val birthDate: String,
    val course: Int,
    val faculty: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val middleName: String,
    val officeEmail: Any,
    val officePassword: Any,
    val photoUrl: String,
    val published: Boolean,
    val rating: Int,
    val references: List<Reference>,
    val searchJob: Boolean,
    val showRating: Boolean,
    val skills: List<Skill>,
    val speciality: String,
    val studentGroup: String,
    val summary: String
)