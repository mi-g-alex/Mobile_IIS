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


class RestorePasswordGetCodeUseCase @Inject constructor(
    private val api_repository: IisApiRepository
) {
    operator fun invoke(
        login: String,
        contactValue: String
    ): Flow<Resource<Any>> = flow {
        try {
            api_repository.restorePasswordGetCode(login, contactValue)
        } catch (_: HttpException) {
        }
    }
}

