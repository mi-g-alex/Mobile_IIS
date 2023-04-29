package by.g_alex.mobile_iis.domain.use_case.login

import android.util.Log
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import coil.network.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RestorePasswordApplyUseCase @Inject constructor(
    private val api_repository: IisApiRepository
) {
    operator fun invoke(
        login: String,
        password: String,
        contactValue: String,
        code: String
    ): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading<Boolean>())
            val resp = api_repository.restorePasswordApply(login, password, contactValue, code)
            emit(Resource.Success<Boolean>(resp?.string()?.isEmpty() == true))
        } catch (e: HttpException) {
            emit(
                Resource.Error<Boolean>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<Boolean>(
                    "Couldn't Find Account"
                )
            )
        }
    }
}