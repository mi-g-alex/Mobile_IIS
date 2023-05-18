package by.g_alex.mobile_iis.domain.use_case.get_profile.diploma

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.diploma.PracticeDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class GetPracticeUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(): Flow<Resource<List<PracticeDto>>> = flow {
        try {
            emit(Resource.Loading<List<PracticeDto>>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<List<PracticeDto>>("LessCookie"))
            }
            if (cookie != null) {
                val data = api_repository.getPractice(cookie)
                emit(Resource.Success<List<PracticeDto>>(data))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<List<PracticeDto>>("LessCookie"))
            } else {
                try {
                    val responseFromLogin =
                        api_repository.loginToAccount(
                            loginAndPassword.login.toString(),
                            loginAndPassword.password.toString()
                        )
                            .awaitResponse()
                    val cookie = responseFromLogin.headers()["Set-Cookie"].toString()
                    val data = api_repository.getPractice(cookie)
                    emit(Resource.Success<List<PracticeDto>>(data))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<List<PracticeDto>>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<List<PracticeDto>>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {
        }
    }
}