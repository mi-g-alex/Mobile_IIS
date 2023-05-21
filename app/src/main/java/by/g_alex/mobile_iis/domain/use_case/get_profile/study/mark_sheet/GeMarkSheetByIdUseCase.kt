package by.g_alex.mobile_iis.domain.use_case.get_profile.study.mark_sheet

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetFocusThIdDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class GeMarkSheetByIdUseCase @Inject constructor(
    private val apiRepository: IisApiRepository,
    private val dbRepository: UserDataBaseRepository
) {
    operator fun invoke(focusId: Int?, thId: Int?): Flow<Resource<List<MarkSheetFocusThIdDto>>> =
        flow {
            try {
                emit(Resource.Loading<List<MarkSheetFocusThIdDto>>())
                val cookie = dbRepository.getCookie()
                if (cookie == null) {
                    emit(Resource.Error<List<MarkSheetFocusThIdDto>>("LessCookie"))
                }
                if (cookie != null) {
                    val data = apiRepository.getMarkSheetById(focusId, thId, cookie)
                    emit(Resource.Success<List<MarkSheetFocusThIdDto>>(data))
                }
            } catch (e: HttpException) {
                val loginAndPassword = dbRepository.getLoginAndPassword()
                if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                    emit(Resource.Error<List<MarkSheetFocusThIdDto>>("LessCookie"))
                } else {
                    try {
                        val responseFromLogin =
                            apiRepository.loginToAccount(
                                loginAndPassword.login.toString(),
                                loginAndPassword.password.toString()
                            )
                                .awaitResponse()
                        val cookie = responseFromLogin.headers()["Set-Cookie"].toString()
                        dbRepository.setCookie(cookie)
                        val data = apiRepository.getMarkSheetById(focusId, thId, cookie)
                        emit(Resource.Success<List<MarkSheetFocusThIdDto>>(data))
                    } catch (e: HttpException) {
                        emit(
                            Resource.Error<List<MarkSheetFocusThIdDto>>(
                                e.localizedMessage ?: "ConnectionError"
                            )
                        )
                    } catch (e: IOException) {
                        emit(
                            Resource.Error<List<MarkSheetFocusThIdDto>>(
                                "LessCookie"
                            )
                        )
                    }
                }
            } catch (e: IOException) {
                emit(
                    Resource.Error<List<MarkSheetFocusThIdDto>>(
                        e.localizedMessage ?: "ConnectionError"
                    )
                )
            }
        }
}