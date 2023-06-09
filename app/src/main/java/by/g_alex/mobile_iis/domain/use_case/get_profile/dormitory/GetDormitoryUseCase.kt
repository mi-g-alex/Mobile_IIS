package by.g_alex.mobile_iis.domain.use_case.get_profile.dormitory

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.local.entity.DormitoryDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class GetDormitoryUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(): Flow<Resource<List<DormitoryDto>>> = flow {
        try {
            emit(Resource.Loading<List<DormitoryDto>>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<List<DormitoryDto>>("LessCookie"))
            }
            if (cookie != null) {
                val data = api_repository.getDormitory(cookie)
                db_repository.deleteDormitory()
                for (n in data) {
                    db_repository.insertDorm(n)
                }
                emit(Resource.Success<List<DormitoryDto>>(data))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<List<DormitoryDto>>("LessCookie"))
            } else {
                try {
                    val responseFromLogin =
                        api_repository.loginToAccount(
                            loginAndPassword.login.toString(),
                            loginAndPassword.password.toString()
                        )
                            .awaitResponse()
                    val cookie = responseFromLogin.headers()["Set-Cookie"].toString()
                    val data = api_repository.getDormitory(cookie)
                    db_repository.deleteDormitory()
                    for (n in data) {
                        db_repository.insertDorm(n)
                    }
                    emit(Resource.Success<List<DormitoryDto>>(data))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<List<DormitoryDto>>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<List<DormitoryDto>>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {
            val data = db_repository.getDorm()
            if (data.isNotEmpty())
                emit(Resource.Success<List<DormitoryDto>>(data))
            else
                emit(
                    Resource.Error<List<DormitoryDto>>("ConnectionError")
                )
        }
    }
}