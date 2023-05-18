package by.g_alex.mobile_iis.domain.use_case.get_profile.personal_cv

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject


class UpdatePhotoUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    suspend fun updatePhoto(data : String) : Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            val data1 = "data:image/jpeg;base64,$data"
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<String>("LessCookie"))
            }
            if (cookie != null) {
                val response =
                    api_repository.updatePhoto(data1, cookie).awaitResponse()
                emit(Resource.Success<String>(response.toString()))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<String>("LessCookie"))
            } else {
                try {
                    val responseFromLogin =
                        api_repository.loginToAccount(
                            loginAndPassword.login.toString(),
                            loginAndPassword.password.toString()
                        )
                            .awaitResponse()
                    val cookie = responseFromLogin.headers()["Set-Cookie"].toString()
                    db_repository.setCookie(cookie)
                    val data1 = "data:image/jpeg;base64,$data"
                    val response =
                        api_repository.updatePhoto(data1, cookie).awaitResponse()
                    emit(Resource.Success<String>(response.toString()))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<String>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<String>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error<String>("error"))
        }
    }
}