package by.g_alex.mobile_iis.data.remote.dto.profile

import by.g_alex.mobile_iis.domain.model.profile.PersonalCV

data class PersonalCVDto(
    val birthDate: String?, // 21.12.2004
    val course: Int?, // 1
    val faculty: String?, // ФКСиС
    val firstName: String?, // Александр
    val id: Int?, // 15266
    val lastName: String?, // Горгун
    val middleName: String?, // Витальевич
    val officeEmail: String?, // emailhere@study.bsuir.by
    val officePassword: String?, // password here
    val photoUrl: String?, // https://drive.google.com/uc?id=1yRyNQ0sEVx3gnnztawY1KBV_uxmKm2B3&export=download
    val published: Boolean?, // true
    val rating: Int?, // 5
    val references: List<ReferenceDto>?,
    val searchJob: Boolean?, // false
    val showRating: Boolean?, // true
    val skills: List<SkillDto>?,
    val speciality: String?, // ИиТП
    val studentGroup: String?, // 253501
    val summary: String? // Text
) {
    fun toPersonalCv(): PersonalCV {
        return PersonalCV(
            birthDate = birthDate, // 21.12.2004
            course = course, // 1
            faculty = faculty, // ФКСиС
            firstName = firstName, // Александр
            id = id, // 15266
            lastName = lastName, // Горгун
            middleName = middleName, // Витальевич
            officeEmail = officeEmail, // 25350033@study.bsuir.by
            officePassword = officePassword, // Qt5oCKKi
            photoUrl = photoUrl, // https//drive.google.com/uc?id=1yRyNQ0sEVx3gnnztawY1KBV_uxmKm2B3&export=download
            published = published, // true
            rating = rating, // 5
            references = references?.map {
                it.toReference()
            },
            searchJob = searchJob, // false
            showRating = showRating, // true
            skills = skills?.map {
                it.toSkill()
            },
            speciality = speciality, // ИиТП
            studentGroup = studentGroup, // 253501
            summary = summary// Text
        )
    }
}