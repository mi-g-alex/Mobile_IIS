package by.g_alex.mobile_iis.domain.use_case.login

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import coil.network.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RestorePasswordEnterLoginUseCase @Inject constructor(
    private val api_repository: IisApiRepository
) {
    operator fun invoke(
        username: String
    ): Flow<Resource<RestorePasswordEnterLoginResponseDto>> = flow {
        try {
            emit(Resource.Loading<RestorePasswordEnterLoginResponseDto>())
            val resp = api_repository.restorePasswordEnterLogin(username)
            if (resp != null)
                emit(Resource.Success<RestorePasswordEnterLoginResponseDto>(resp))
            else emit(Resource.Error<RestorePasswordEnterLoginResponseDto>("Не найдено"))
        } catch (e: HttpException) {
            emit(
                Resource.Error<RestorePasswordEnterLoginResponseDto>(
                    e.localizedMessage ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(
                Resource.Error<RestorePasswordEnterLoginResponseDto>(
                    "Couldn't Find Account"
                )
            )
        }
    }

}
