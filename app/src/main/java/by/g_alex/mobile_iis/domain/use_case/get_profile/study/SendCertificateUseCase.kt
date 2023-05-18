package by.g_alex.mobile_iis.domain.use_case.get_profile.study

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.study.SendCertificateDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyCertificationsDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class SendCertificateUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository : UserDataBaseRepository
) {
    operator fun invoke(
        request: SendCertificateDto
    ): Flow<Resource<Response<List<StudyCertificationsDto>>>> = flow {
        try {
            emit(Resource.Loading<Response<List<StudyCertificationsDto>>>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<Response<List<StudyCertificationsDto>>>("LessCookie"))
            }
            if (cookie != null) {
                val response = api_repository.sendCertificate(request = request, token = cookie).awaitResponse()
                emit(Resource.Success<Response<List<StudyCertificationsDto>>>(response))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<Response<List<StudyCertificationsDto>>>("LessCookie"))
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
                    val response = api_repository.sendCertificate(request = request, token = cookie).awaitResponse()
                    emit(Resource.Success<Response<List<StudyCertificationsDto>>>(response))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<Response<List<StudyCertificationsDto>>>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<Response<List<StudyCertificationsDto>>>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {

        }
    }
}