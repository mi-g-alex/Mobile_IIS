package by.g_alex.mobile_iis.data.local

import androidx.room.*
import by.g_alex.mobile_iis.data.local.entity.*

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

    @Query("SELECT * FROM LessonModel WHERE lessonTypeAbbrev = :abbv AND id = :group")
    suspend fun getScheduleByAbbv(abbv: String,group: String): List<LessonModel>

    @Query("SELECT * FROM MarkBookEntity")
    suspend fun getMarkBook():List<MarkBookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkBook(markbook: MarkBookEntity)

    @Query("DELETE FROM MarkBookEntity")
    suspend fun deleteMarkbooks()

    @Query("SELECT * FROM OmissionsByStudentDto")
    suspend fun getOmissions():List<OmissionsByStudentDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOmissions(omission: OmissionsByStudentDto)

    @Query("DELETE FROM OmissionsByStudentDto")
    suspend fun deleteOmissions()

    @Query("SELECT * FROM DormitoryDto")
    suspend fun getDormitory():List<DormitoryDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDormitory(dorm: DormitoryDto)

    @Query("DELETE FROM DormitoryDto")
    suspend fun deleteDormitory()

    @Query("SELECT * FROM PrivilegesDto")
    suspend fun getPriivileges():List<PrivilegesDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrivilege(privilege: PrivilegesDto)

    @Query("DELETE FROM PenaltyModel")
    suspend fun deletePenalty()

    @Query("SELECT * FROM PenaltyModel")
    suspend fun getPenalty():List<PenaltyModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPenalty(penalty : PenaltyModel)

    @Query("DELETE FROM PrivilegesDto")
    suspend fun deletePrivileges()

    @Query("SELECT * FROM GradeBookEntity")
    suspend fun getGradeBook():List<GradeBookEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGradeBook(gradebook: GradeBookEntity)

    @Query("DELETE FROM GradeBookEntity")
    suspend fun deleteGradebooks()

    @Query("DELETE FROM LessonModel WHERE id LIKE :name")
    suspend fun deleteSchedulebyName(name:String)

    @Query("DELETE FROM LessonModel WHERE lessonTypeAbbrev = :name AND id = :group")
    suspend fun deleteSchedulebyAbbv(name:String,group: String)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(users: LessonModel)
}