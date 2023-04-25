package by.g_alex.mobile_iis.data.remote

import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.data.remote.dto.dormitory.DormitoryDto
import by.g_alex.mobile_iis.data.remote.dto.dormitory.PrivilegesDto
import by.g_alex.mobile_iis.data.remote.dto.employee.EmployeeListItemDto
import by.g_alex.mobile_iis.data.remote.dto.faculties.FacultiesDto
import by.g_alex.mobile_iis.data.remote.dto.grade_book.GradeBookDto
import by.g_alex.mobile_iis.data.remote.dto.group.GroupDtoItem
import by.g_alex.mobile_iis.data.remote.dto.login.*
import by.g_alex.mobile_iis.data.remote.dto.mark_book.MarkBookDto
import by.g_alex.mobile_iis.data.remote.dto.omissions.OmissionsByStudentDto
import by.g_alex.mobile_iis.data.remote.dto.penalty.PenaltyDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
import by.g_alex.mobile_iis.data.remote.dto.rating.RatingDto
import by.g_alex.mobile_iis.data.remote.dto.schedule.MainDto
import by.g_alex.mobile_iis.data.remote.dto.specialities.SpecialityDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyApplicationsDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyCertificationsDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyMarkSheetDto
import by.g_alex.mobile_iis.data.remote.dto.use_group.UserGroupDto
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface IisApi {

    // For AUTH

    @POST("api/v1/auth/login") // Авторизация
    fun loginToAccount(@Body request: LoginAndPasswordDto): Call<LoginResponseDto>

    @GET("api/v1/auth/logout") // Выход
    suspend fun logout(@Header("Cookie") cookieValue: String)

    @GET("api/v1/settings/masked-contacts?")
    fun restorePasswordEnterLogin(@Query("login") username: String): Call<RestorePasswordEnterLoginResponseDto>

    @POST("api/v1/settings/contact/exist")
    fun restorePasswordCheckExist(@Body request: RestorePasswordCheckSendDto): Call<RestorePasswordEnterLoginResponseDto>

    @POST("api/v1/settings/password/reset")
    fun restorePasswordGetCode(@Body request: RestorePasswordCheckSendDto) : Call<Any>

    @POST("api/v1/settings/password/new")
    fun restorePasswordApply(@Body request: RestorePasswordApplyDto) : Call<Any>

    // User Info
    @GET("api/v1/profiles/personal-cv") // Инфомарция о профиле / Главный экран
    suspend fun getProfilePersonCV(@Header("Cookie") cookieValue: String): PersonalCVDto

    @POST("api/v1/profiles/my-photo") // Смена фото
    fun updatePhoto(@Body body: RequestBody, @Header("Cookie") cookieValue: String): Call<String>

    @GET("api/v1/markbook") // Зачётка
    suspend fun getMarkBook(@Header("Cookie") cookieValue: String): MarkBookDto

    @GET("api/v1/student-groups/user-group-info") // Группа
    suspend fun getUserGroup(@Header("Cookie") cookieValue: String): UserGroupDto

    @GET("api/v1/grade-book") // Рейтинг
    suspend fun getGradeBook(@Header("Cookie") cookieValue: String): List<GradeBookDto>

    @GET("api/v1/omissions-by-student") // Пропуски
    suspend fun getOmissionsByStudent(@Header("Cookie") cookieValue: String): List<OmissionsByStudentDto>

    // Учёба
    @GET("api/v1/mark-sheet") // Ведомостички
    suspend fun getStudyMarkSheet(@Header("Cookie") cookieValue: String): List<StudyMarkSheetDto>

    @GET("api/v1/certificate") // Справки
    suspend fun getStudyCertificate(@Header("Cookie") cookieValue: String): List<StudyCertificationsDto>

    @GET("api/v1/lms/application-history") // ДОТ
    suspend fun getStudyApplications(@Header("Cookie") cookieValue: String): List<StudyApplicationsDto>

    @GET("api/v1/dormitory-queue-application/privileges")
    suspend fun getPrivileges(@Header("Cookie") cookieValue: String): List<PrivilegesDto>

    @GET("api/v1/library/debts") // Задолженности библиотека
    suspend fun getStudyLibDebts(@Header("Cookie") cookieValue: String): List<String>

    @GET("api/v1/announcements") // Обьявления
    suspend fun getAnnouncements(@Header("Cookie") cookieValue: String): List<AnnouncemntDto>

    @GET("api/v1/dormitory-queue-application")
    suspend fun getDormitory(@Header("Cookie") cookieValue: String): List<DormitoryDto>

    @GET("api/v1/dormitory-queue-application/premium-penalty")
    suspend fun getPenalty(@Header("Cookie") cookieValue: String): List<PenaltyDto>
    // For all

    @GET("api/v1/schedule?") // Расписание группы
    suspend fun getSchedule(@Query("studentGroup") groupNumber: String): MainDto

    @GET("api/v1/employees/schedule/{urlId}") // Получние расписания преподавателя
    suspend fun getEmployeeSchedule(@Path("urlId") urlId: String): MainDto?

    @GET("api/v1/schedule/current-week") // Текущая неделя
    suspend fun getCurrentWeek(): Int

    @GET("api/v1/student-groups") // Группы студентов
    suspend fun getGroups(): List<GroupDtoItem>

    @GET("api/v1/employees/all") // Список преподавателей
    suspend fun getEmployeesList(): List<EmployeeListItemDto>

    @GET("api/v1/schedule/faculties")
    suspend fun getFaculties():List<FacultiesDto>

    @GET("api/v1/rating/specialities")
    suspend fun getSpecialities(@Query("facultyId") id : Int, @Query("entryYear") year:Int):List<SpecialityDto>
    @GET("api/v1/rating")
    suspend fun getRating(@Query("year") year : Int, @Query("sdef") sdef:Int):List<RatingDto>

}