package by.g_alex.mobile_iis.domain.use_case.get_profile.dormitory

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.local.entity.PrivilegesDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class GetPrivilegesUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(): Flow<Resource<List<PrivilegesDto>>> = flow {
        try {
            emit(Resource.Loading<List<PrivilegesDto>>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<List<PrivilegesDto>>("LessCookie"))
            }
            if (cookie != null) {
                val data = api_repository.getPrivileges(cookie)
                db_repository.deletePrivileges()
                for (n in data) {
                    db_repository.insertPrivilege(n)
                }
                emit(Resource.Success<List<PrivilegesDto>>(data))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<List<PrivilegesDto>>("LessCookie"))
            } else {
                try {
                    val responseFromLogin =
                        api_repository.loginToAccount(
                            loginAndPassword.login.toString(),
                            loginAndPassword.password.toString()
                        )
                            .awaitResponse()
                    val cookie = responseFromLogin.headers()["Set-Cookie"].toString()
                    val data = api_repository.getPrivileges(cookie)
                    db_repository.deletePrivileges()
                    for (n in data) {
                        db_repository.insertPrivilege(n)
                    }
                    emit(Resource.Success<List<PrivilegesDto>>(data))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<List<PrivilegesDto>>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<List<PrivilegesDto>>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {
            val data = db_repository.getPrivileges()
            if (data.isNotEmpty())
                emit(Resource.Success<List<PrivilegesDto>>(data))
            else
                emit(
                    Resource.Error<List<PrivilegesDto>>("ConnectionError")
                )
        }
    }
}