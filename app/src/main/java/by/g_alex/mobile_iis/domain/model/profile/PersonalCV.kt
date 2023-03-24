package by.g_alex.mobile_iis.domain.model.profile

import by.g_alex.mobile_iis.data.local.entity.ProfilePersonalCVEntity

data class PersonalCV(
    val birthDate: String?, // 21.12.2004
    val course: Int?, // 1
    val faculty: String?, // ФКСиС
    val firstName: String?, // Александр
    val id: Int?, // 15266
    val lastName: String?, // Горгун
    val middleName: String?, // Витальевич
    val officeEmail: String?, // 25350033@study.bsuir.by
    val officePassword: String?, // Qt5oCKKi
    val photoUrl: String?, // https://drive.google.com/uc?id=1yRyNQ0sEVx3gnnztawY1KBV_uxmKm2B3&export=download
    val published: Boolean?, // true
    val rating: Int?, // 5
    val references: List<Reference>?,
    val searchJob: Boolean?, // false
    val showRating: Boolean?, // true
    val skills: List<Skill>?,
    val speciality: String?, // ИиТП
    val studentGroup: String?, // 253501
    val summary: String? // Text
) {
    fun toEntity(): ProfilePersonalCVEntity =
        ProfilePersonalCVEntity(
            alwaysFiled = 0,
            birthDate,
            course,
            faculty,
            firstName,
            id,
            lastName,
            middleName,
            officeEmail,
            officePassword,
            photoUrl,
            published,
            rating,
            references,
            searchJob,
            showRating,
            skills,
            speciality,
            studentGroup,
            summary
        )
}