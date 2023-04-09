package by.g_alex.mobile_iis.data.local

import androidx.room.*
import by.g_alex.mobile_iis.data.local.entity.CookieEntity
import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.local.entity.LoginAndPasswordEntity
import by.g_alex.mobile_iis.data.local.entity.ProfilePersonalCVEntity

@Dao
interface UserDao {

    // Cookie
    @Query("SELECT * FROM CookieEntity WHERE alwaysField = 0")
    suspend fun getCookie(): CookieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCookie(cookie: CookieEntity)

    @Query("DELETE FROM CookieEntity")
    suspend fun deleteCookie()

    // Login&Password
    @Query("SELECT * FROM LoginAndPasswordEntity WHERE alwaysField = 0")
    suspend fun getLoginAndPassword(): LoginAndPasswordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setLoginAndPassword(loginAndPassword: LoginAndPasswordEntity)

    @Query("DELETE FROM LoginAndPasswordEntity")
    suspend fun deleteLoginAndPassword()


    // Profile/PersonalCV
    @Query("SELECT * FROM ProfilePersonalCVEntity WHERE alwaysFiled = 0")
    suspend fun getProfilePersonalCV(): ProfilePersonalCVEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setProfilePersonalCV(profilePersonalCVEntity: ProfilePersonalCVEntity)

    @Query("DELETE FROM ProfilePersonalCVEntity")
    suspend fun deleteProfilePersonalCV()

    @Query("SELECT * FROM LessonModel WHERE id LIKE :group")
    suspend fun getSchedule(group: String): List<LessonModel>

    @Query("DELETE FROM LessonModel WHERE id LIKE :name")
    suspend fun deleteSchedulebyName(name:String)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(users: LessonModel)
}