package by.g_alex.mobile_iis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.g_alex.mobile_iis.domain.model.profile.PersonalCV
import by.g_alex.mobile_iis.domain.model.profile.Reference
import by.g_alex.mobile_iis.domain.model.profile.Skill

@Entity
data class ProfilePersonalCVEntity(
    @PrimaryKey val alwaysFiled: Int = 0,
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
    val references: List<Reference>?,
    val searchJob: Boolean?, // false
    val showRating: Boolean?, // true
    val skills: List<Skill>?,
    val speciality: String?, // ИиТП
    val studentGroup: String?, // 253501
    val summary: String?, // Text
) {
    fun toModel() =
        PersonalCV(
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
