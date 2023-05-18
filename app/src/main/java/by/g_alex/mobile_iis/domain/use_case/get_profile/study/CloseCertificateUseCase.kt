package by.g_alex.mobile_iis.domain.use_case.get_profile.study

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class CloseCertificateUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(id: Int): Flow<Resource<Any>> = flow {
        try {
            emit(Resource.Loading<Any>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<Any>("LessCookie"))
            }
            if (cookie != null) {
                val data = api_repository.closeCertificate(id, cookie)
                emit(Resource.Success<Any>(data))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<Any>("LessCookie"))
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
                    val data = api_repository.closeCertificate(id, cookie)
                    emit(Resource.Success<Any>(data))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<Any>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<Any>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {

        }
    }
}