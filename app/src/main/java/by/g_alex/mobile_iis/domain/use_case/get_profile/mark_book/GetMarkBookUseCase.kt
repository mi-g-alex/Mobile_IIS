package by.g_alex.mobile_iis.domain.use_case.get_profile.mark_book

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel
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
    operator fun invoke(): Flow<Resource<List<MarkBookMarkModel>>> = flow {
        try {
            emit(Resource.Loading<List<MarkBookMarkModel>>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<List<MarkBookMarkModel>>("LessCookie"))
            }
            if (cookie != null) {
                val data = api_repository.getMarkBook(cookie)
                emit(Resource.Success<List<MarkBookMarkModel>>(data))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<List<MarkBookMarkModel>>("LessCookie"))
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
                    emit(Resource.Success<List<MarkBookMarkModel>>(data))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<List<MarkBookMarkModel>>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<List<MarkBookMarkModel>>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {

        }
    }
}