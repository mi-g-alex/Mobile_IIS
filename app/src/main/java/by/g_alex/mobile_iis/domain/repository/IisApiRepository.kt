package by.g_alex.mobile_iis.domain.repository

import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.omissions.OmissionsByStudentDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyDto
import by.g_alex.mobile_iis.data.remote.dto.use_group.UserGroupDto
import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.GroupModel
import retrofit2.Call

interface IisApiRepository {

    // For AUTH
    suspend fun loginToAccount(
        username: String,
        password: String
    ): Call<LoginResponseDto>

    suspend fun getProfilePersonalCV(token: String): PersonalCVDto

    suspend fun updatePhoto(request: String, token: String): Call<String>

    suspend fun getGradeBook(cookie: String): List<GradeBookLessonModel>

    suspend fun logout(token: String)

    suspend fun getMarkBook(token: String): List<MarkBookMarkModel>

    suspend fun getUserGroup(token: String): UserGroupDto

    suspend fun getOmissionsByStudent(token: String): List<OmissionsByStudentDto>

    suspend fun getStudy(token: String): StudyDto

    suspend fun getAnnouncements(token:String):List<AnnouncemntDto>
    // For All

    suspend fun getSchedule(groupNum: String): List<LessonModel>?

    suspend fun getCurrentWeek(): Int

    suspend fun getGroups(): List<GroupModel>

    suspend fun getEmployees(): List<EmployeeModel>

    suspend fun getEmployeeSchedule(urlId: String): List<LessonModel>
}