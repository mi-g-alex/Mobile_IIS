package by.g_alex.mobile_iis.domain.repository

import by.g_alex.mobile_iis.data.local.entity.DormitoryDto
import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.local.entity.LoginAndPasswordEntity
import by.g_alex.mobile_iis.data.local.entity.OmissionsByStudentDto
import by.g_alex.mobile_iis.data.local.entity.PenaltyModel
import by.g_alex.mobile_iis.data.local.entity.PrivilegesDto
import by.g_alex.mobile_iis.domain.model.profile.PersonalCV
import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel

interface UserDataBaseRepository {

    // Cookie
    suspend fun getCookie(): String?

    suspend fun setCookie(cookie: String?)

    suspend fun deleteCookie()

    // Login&Password
    suspend fun getLoginAndPassword(): LoginAndPasswordEntity?

    suspend fun setLoginAndPassword(username: String, password: String)

    suspend fun deleteLoginAndPassword()

    // Profile/PersonalCV
    suspend fun getProfilePersonalCV(): PersonalCV?

    suspend fun getMarkBooks(): List<MarkBookMarkModel>

    suspend fun insertMarkBook(markbook: MarkBookMarkModel)

    suspend fun deleteMarkBooks()

    suspend fun getOmissions():List<OmissionsByStudentDto>

    suspend fun insertOmission(omisson: OmissionsByStudentDto)

    suspend fun deleteOmissions()

    suspend fun getDorm():List<DormitoryDto>

    suspend fun insertDorm(dorm: DormitoryDto)

    suspend fun deleteDormitory()

    suspend fun getPrivileges():List<PrivilegesDto>

    suspend fun insertPrivilege(privilege: PrivilegesDto)

    suspend fun deletePrivileges()

    suspend fun getPenalty():List<PenaltyModel>

    suspend fun insertPenalty(penalty: PenaltyModel)

    suspend fun deletePenalty()


    suspend fun getGradeBook(): List<GradeBookLessonModel>

    suspend fun insertGradeBook(gradebook: GradeBookLessonModel)

    suspend fun deleteGradeBooks()
    suspend fun setProfilePersonalCV(personalCV: PersonalCV)

    suspend fun deleteProfilePersonalCV()

    suspend fun deleteSchedulebyName(name: String)

    // Schedule
    suspend fun getSchedule(group: String): List<LessonModel>

    suspend fun getScheduleByAbbv(abbv: String,group: String): List<LessonModel>

    suspend fun deleteSchedulebyAbbv(name:String,group: String)
    suspend fun insertSchedule(users: LessonModel)
}