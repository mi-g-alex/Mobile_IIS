package by.g_alex.mobile_iis.domain.use_case.get_profile.omissions

import android.util.Log
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.local.entity.OmissionsByStudentDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class GetOmissionsUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(): Flow<Resource<List<OmissionsByStudentDto>>> = flow {
        try {
            emit(Resource.Loading<List<OmissionsByStudentDto>>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<List<OmissionsByStudentDto>>("LessCookie"))
            }
            if (cookie != null) {
                val data = api_repository.getOmissionsByStudent(cookie)
                Log.e("~~~", data.toString())
                db_repository.deleteOmissions()
                for (n in data) {
                    db_repository.insertOmission(n)
                }
                emit(Resource.Success<List<OmissionsByStudentDto>>(data))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<List<OmissionsByStudentDto>>("LessCookie"))
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
                    val data = api_repository.getOmissionsByStudent(cookie)
                    db_repository.deleteOmissions()
                    for (n in data) {
                        db_repository.insertOmission(n)
                    }
                    emit(Resource.Success<List<OmissionsByStudentDto>>(data))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<List<OmissionsByStudentDto>>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<List<OmissionsByStudentDto>>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {
            val data = db_repository.getOmissions()
            if (data.isNotEmpty())
                emit(Resource.Success<List<OmissionsByStudentDto>>(data))
            else
                emit(
                    Resource.Error<List<OmissionsByStudentDto>>("ConnectionError")
                )
        }
    }
}