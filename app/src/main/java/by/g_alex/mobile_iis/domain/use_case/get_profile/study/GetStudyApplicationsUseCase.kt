package by.g_alex.mobile_iis.domain.use_case.get_profile.study

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.study.StudyApplicationsDto
import by.g_alex.mobile_iis.data.remote.dto.use_group.UserGroupDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class GetStudyApplicationsDtoUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(): Flow<Resource<List<StudyApplicationsDto>>> = flow {
        try {
            emit(Resource.Loading<List<StudyApplicationsDto>>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<List<StudyApplicationsDto>>("LessCookie"))
            }
            if (cookie != null) {
                val data = api_repository.getStudyApplications(cookie)
                emit(Resource.Success<List<StudyApplicationsDto>>(data))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<List<StudyApplicationsDto>>("LessCookie"))
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
                    val data = api_repository.getStudyApplications(cookie)
                    emit(Resource.Success<List<StudyApplicationsDto>>(data))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<List<StudyApplicationsDto>>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<List<StudyApplicationsDto>>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {

        }
    }
}