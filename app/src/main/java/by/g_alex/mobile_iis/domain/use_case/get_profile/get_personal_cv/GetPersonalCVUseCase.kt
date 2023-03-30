package by.g_alex.mobile_iis.domain.use_case.get_profile.get_personal_cv

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

class GetPersonalCVUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(): Flow<Resource<PersonalCV>> = flow {
        try {
            emit(Resource.Loading<PersonalCV>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                val loginAndPassword = db_repository.getLoginAndPassword()
                if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                    emit(Resource.Error<PersonalCV>("LessCookie"))
                } else {
                    try {
                        val responseFromLogin =
                            api_repository.loginToAccount(
                                loginAndPassword.login,
                                loginAndPassword.password
                            )
                                .awaitResponse()
                        val newCookie = responseFromLogin.headers()["Set-Cookie"].toString()
                        db_repository.setCookie(newCookie)
                        val data = api_repository.getProfilePersonalCV(newCookie).toPersonalCv()
                        db_repository.setProfilePersonalCV(data)
                        emit(Resource.Success<PersonalCV>(data))
                    } catch (e: HttpException) {
                        emit(
                            Resource.Error<PersonalCV>(e.localizedMessage ?: "LessCookie")
                        )
                    } catch (e: IOException) {
                        emit(
                            Resource.Error<PersonalCV>(
                                e.message.toString()
                            )
                        )
                    }
                }


            }
            if (cookie != null) {
                val data = api_repository.getProfilePersonalCV(cookie).toPersonalCv()
                db_repository.setProfilePersonalCV(data)
                emit(Resource.Success<PersonalCV>(data))
            }
        } catch (e: HttpException) {
            Log.e("123", e.toString())
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
                    val data = api_repository.getProfilePersonalCV(cookie).toPersonalCv()
                    db_repository.setProfilePersonalCV(data)
                    emit(Resource.Success<PersonalCV>(data))
                } catch (e: HttpException) {
                    Log.e("|123", e.toString())
                    emit(
                        Resource.Error<PersonalCV>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    Log.e("||123", e.toString())
                    emit(
                        Resource.Error<PersonalCV>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (e: IOException) {
            val data = db_repository.getProfilePersonalCV()
            Log.e("12312341234123", data.toString())
            emit(Resource.Success<PersonalCV>(data))
        }
    }
}