package by.g_alex.mobile_iis.domain.use_case.login

import android.util.Log
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import coil.network.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import retrofit2.awaitResponse
import java.io.IOException

class LoginToAccountUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository : UserDataBaseRepository
) {
    operator fun invoke(
        username: String,
        password: String
    ): Flow<Resource<Response<LoginResponseDto>>> = flow {
        try {
            emit(Resource.Loading<Response<LoginResponseDto>>())
            val responseFromLogin =
                api_repository.loginToAccount(username, password).awaitResponse()
            val cookie = responseFromLogin.headers()["Set-Cookie"].toString()
            db_repository.setCookie(cookie)
            db_repository.setLoginAndPassword(username, password)
            emit(Resource.Success<Response<LoginResponseDto>>(responseFromLogin))
        } catch (e: HttpException) {
            emit(
                Resource.Error<Response<LoginResponseDto>>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            Log.e("Login Error", e.toString())
            emit(
                Resource.Error<Response<LoginResponseDto>>(
                    "Couldn't Login"
                )
            )
        }
    }

}

