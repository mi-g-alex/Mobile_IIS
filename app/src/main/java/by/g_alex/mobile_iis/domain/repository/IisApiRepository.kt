package by.g_alex.mobile_iis.domain.repository

import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
import retrofit2.Call

interface IisApiRepository {
    suspend fun loginToAccount(
        username : String,
        password : String
    ) : Call<LoginResponseDto>

    suspend fun getProfilePersonalCV(token : String) : PersonalCVDto

    suspend fun logout(token : String)
}