package by.g_alex.mobile_iis.data.remote

import by.g_alex.mobile_iis.data.remote.dto.login.LoginAndPasswordDto
import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
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

}