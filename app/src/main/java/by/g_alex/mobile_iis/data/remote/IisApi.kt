package by.g_alex.mobile_iis.data.remote

import by.g_alex.mobile_iis.data.remote.dto.login.LoginAndPasswordDto
import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
import com.example.compose.data.remote.dto.Schedule.MainDto
import com.example.compose.data.remote.dto.group.GroupDtoItem
import com.example.compose.data.remote.dto.prepod.PrepodDto
import com.example.compose.data.remote.dto.prepodSchedule.PrepScheduleDto
import retrofit2.Call
import retrofit2.http.*

interface IisApi {

    @POST("/api/v1/auth/login")
    fun loginToAccount(
        @Body request : LoginAndPasswordDto
    ) : Call<LoginResponseDto>

    @GET("/api/v1/profiles/personal-cv")
    suspend fun getProfilePersonCV(@Header("Cookie") cookieValue : String) : PersonalCVDto

    @GET("api/v1/auth/logout")
    suspend fun logout(@Header("Cookie") cookieValue: String)

    @GET("api/v1/schedule?")
    suspend fun getShedule(@Query("studentGroup") groupNumber : String): MainDto
    @GET("api/v1/schedule/current-week")
    suspend fun getCurrentWeek():Int
    @GET("api/v1/student-groups")
    suspend fun getGroups():List<GroupDtoItem>
    @GET("api/v1/employees/all")
    suspend fun getPrepod():List<PrepodDto>
    @GET("api/v1/employees/schedule/{urlId}")
    suspend fun getPrepShedule(@Path("urlId") urlId : String): PrepScheduleDto

}