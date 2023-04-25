package by.g_alex.mobile_iis.domain.use_case.get_profile.settings

import android.util.Log
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.model.profile.PersonalCV
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class PutSummaryUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    suspend fun putSummary(cvDto: PersonalCV): Flow<Resource<PersonalCV>> = flow {
        try {
            emit(Resource.Loading<PersonalCV>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<PersonalCV>("LessCookie"))
            }
            if (cookie != null) {
                api_repository.putSummary(cookie, cvDto)
                db_repository.setProfilePersonalCV(cvDto)
                emit(Resource.Success<PersonalCV>(cvDto))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<PersonalCV>("LessCookie"))
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
                    val data = api_repository.putSummary(cookie, cvDto)
                    db_repository.setProfilePersonalCV(cvDto)
                    emit(Resource.Success<PersonalCV>(cvDto))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<PersonalCV>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<PersonalCV>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            emit(
                Resource.Error<PersonalCV>(
                    "Error"
                )
            )
        }
    }
}