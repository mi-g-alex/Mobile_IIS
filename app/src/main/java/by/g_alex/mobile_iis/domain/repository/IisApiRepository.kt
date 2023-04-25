package by.g_alex.mobile_iis.domain.repository

import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.data.remote.dto.dormitory.DormitoryDto
import by.g_alex.mobile_iis.data.remote.dto.dormitory.PrivilegesDto
import by.g_alex.mobile_iis.data.remote.dto.faculties.FacultiesDto
import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.omissions.OmissionsByStudentDto
import by.g_alex.mobile_iis.data.remote.dto.personal_rating.PersonalRatingDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
import by.g_alex.mobile_iis.data.remote.dto.rating.RatingDto
import by.g_alex.mobile_iis.data.remote.dto.settings.ConfirmEmailDto
import by.g_alex.mobile_iis.data.remote.dto.settings.ContactsDto
import by.g_alex.mobile_iis.data.remote.dto.settings.EmailChangeDto
import by.g_alex.mobile_iis.data.remote.dto.specialities.SpecialityDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyDto
import by.g_alex.mobile_iis.data.remote.dto.use_group.UserGroupDto
import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel
import by.g_alex.mobile_iis.domain.model.profile.penalty_model.PenaltyModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.GroupModel
import retrofit2.Call

interface IisApiRepository {

    // For AUTH
    suspend fun loginToAccount(
        username: String,
        password: String
    ): Call<LoginResponseDto>

    suspend fun logout(token: String)

    suspend fun restorePasswordEnterLogin(username: String): RestorePasswordEnterLoginResponseDto?

    suspend fun restorePasswordCheckExist(
        login: String,
        contactValue: String
    ): RestorePasswordEnterLoginResponseDto?

    suspend fun restorePasswordGetCode(login: String, contactValue: String)

    suspend fun restorePasswordApply(
        login: String,
        password: String,
        contactValue: String,
        code: String
    ): Boolean

    // For USER
    suspend fun getProfilePersonalCV(token: String): PersonalCVDto

    suspend fun updatePhoto(request: String, token: String): Call<String>

    suspend fun getGradeBook(cookie: String): List<GradeBookLessonModel>

    suspend fun getMarkBook(token: String): List<MarkBookMarkModel>

    suspend fun getPrivileges(token: String): List<PrivilegesDto>
    suspend fun getUserGroup(token: String): UserGroupDto

    suspend fun getOmissionsByStudent(token: String): List<OmissionsByStudentDto>

    suspend fun getStudy(token: String): StudyDto

    suspend fun getAnnouncements(token: String): List<AnnouncemntDto>

    suspend fun getDormitory(token: String): List<DormitoryDto>

    suspend fun getPenalty(token: String): List<PenaltyModel>

    suspend fun getEmail(token: String): ContactsDto

    suspend fun updateEmail(token: String, email: EmailChangeDto): Call<Any?>

    suspend fun getCodeForEmail(token: String, id: Int): Call<Any>

    suspend fun confirmCodeForEmail(token: String, email: ConfirmEmailDto): Call<Any>


    // For All
    suspend fun getSchedule(groupNum: String): List<LessonModel>?

    suspend fun getCurrentWeek(): Int

    suspend fun getGroups(): List<GroupModel>

    suspend fun getEmployees(): List<EmployeeModel>

    suspend fun getEmployeeSchedule(urlId: String): List<LessonModel>

    suspend fun getFaculties(): List<FacultiesDto>

    suspend fun getSpecialities(year: Int, id: Int): List<SpecialityDto>

    suspend fun getRating(year: Int, id: Int): List<RatingDto>

    suspend fun getPersonalRating(number: String): PersonalRatingDto
}