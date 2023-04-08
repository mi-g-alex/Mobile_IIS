package by.g_alex.mobile_iis.domain.repository

import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.local.entity.LoginAndPasswordEntity
import by.g_alex.mobile_iis.domain.model.profile.PersonalCV

interface UserDataBaseRepository {

    // Cookie
    suspend fun getCookie(): String?

    suspend fun setCookie(cookie : String?)

    suspend fun deleteCookie()

    // Login&Password
    suspend fun getLoginAndPassword(): LoginAndPasswordEntity?

    suspend fun setLoginAndPassword(username : String, password : String)

    suspend fun deleteLoginAndPassword()

    // Profile/PersonalCV
    suspend fun getProfilePersonalCV(): PersonalCV?

    suspend fun setProfilePersonalCV(personalCV: PersonalCV)

    suspend fun deleteProfilePersonalCV()

    suspend fun deleteSchedulebyName(name:String)
    // Schedule
    suspend fun getSchedule(group: String): List<LessonModel>

    suspend fun insertSchedule(users: LessonModel)
}