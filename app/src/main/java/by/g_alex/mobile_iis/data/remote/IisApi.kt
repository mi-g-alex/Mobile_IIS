package by.g_alex.mobile_iis.data.remote

import by.g_alex.mobile_iis.data.remote.dto.login.LoginAndPasswordDto
import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
import by.g_alex.mobile_iis.data.remote.dto.schedule.MainDto
import by.g_alex.mobile_iis.data.remote.dto.group.GroupDtoItem
import by.g_alex.mobile_iis.data.remote.dto.employee.EmployeeListItemDto
import by.g_alex.mobile_iis.data.remote.dto.grade_book.GradeBookDto
import by.g_alex.mobile_iis.data.remote.dto.omissions.OmissionsByStudentDto
import by.g_alex.mobile_iis.data.remote.dto.use_group.UserGroupDto
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface IisApi {

    // For AUTH

    @POST("api/v1/auth/login")
    fun loginToAccount(@Body request: LoginAndPasswordDto): Call<LoginResponseDto>

    @GET("api/v1/profiles/personal-cv")
    suspend fun getProfilePersonCV(@Header("Cookie") cookieValue: String): PersonalCVDto

    @POST("api/v1/profiles/my-photo")
    fun updatePhoto(@Body body: RequestBody, @Header("Cookie") cookieValue: String) : Call<String>

    @GET("api/v1/auth/logout")
    suspend fun logout(@Header("Cookie") cookieValue: String)

    @GET("api/v1/markbook")
    suspend fun getMarkBook(@Header("Cookie") cookieValue: String) : by.g_alex.mobile_iis.data.remote.dto.mark_book.MarkBookDto

    @GET("api/v1/student-groups/user-group-info")
    suspend fun getUserGroup(@Header("Cookie") cookieValue: String) : UserGroupDto

    @GET("api/v1/grade-book")
    suspend fun getGradeBook(@Header("Cookie") cookieValue: String) : List<GradeBookDto>

    @GET("api/v1/omissions-by-student")
    suspend fun getOmissionsByStudent(@Header("Cookie") cookieValue: String) : List<OmissionsByStudentDto>

    // For all

    @GET("api/v1/schedule?")
    suspend fun getSchedule(@Query("studentGroup") groupNumber: String): MainDto

    @GET("api/v1/schedule/current-week")
    suspend fun getCurrentWeek(): Int

    @GET("api/v1/student-groups")
    suspend fun getGroups(): List<GroupDtoItem>

    @GET("api/v1/employees/all")
    suspend fun getEmployeesList(): List<EmployeeListItemDto>

    @GET("api/v1/employees/schedule/{urlId}")
    suspend fun getEmployeeSchedule(@Path("urlId") urlId: String): MainDto?

}