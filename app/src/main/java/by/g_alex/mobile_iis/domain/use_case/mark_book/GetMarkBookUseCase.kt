package by.g_alex.mobile_iis.domain.use_case.mark_book

import android.util.Log
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.mark_book.MarkBookDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class GetMarkBookUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(): Flow<Resource<MarkBookDto>> = flow {
        try {
            emit(Resource.Loading<MarkBookDto>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                val loginAndPassword = db_repository.getLoginAndPassword()
                if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                    emit(Resource.Error<MarkBookDto>("LessCookie"))
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
                        val data = api_repository.getMarkBook(newCookie)
                        emit(Resource.Success<MarkBookDto>(data))
                    } catch (e: HttpException) {
                        emit(
                            Resource.Error<MarkBookDto>(e.localizedMessage ?: "LessCookie")
                        )
                    } catch (e: IOException) {
                        emit(
                            Resource.Error<MarkBookDto>(
                                e.message.toString()
                            )
                        )
                    }
                }


            }
            if (cookie != null) {
                val data = api_repository.getMarkBook(cookie)
                emit(Resource.Success<MarkBookDto>(data))
            }
        } catch (e: HttpException) {
            Log.e("123", e.toString())
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<MarkBookDto>("LessCookie"))
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
                    val data = api_repository.getMarkBook(cookie)
                    emit(Resource.Success<MarkBookDto>(data))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<MarkBookDto>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<MarkBookDto>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {

        }
    }
}