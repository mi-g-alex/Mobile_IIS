package by.g_alex.mobile_iis.domain.use_case.feedback

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SendFeedbackUseCase @Inject constructor(
    private val api: IisApiRepository
) {
    operator fun invoke(text: String, link: String?): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.sendFeedback(text, link)
            if (response?.string()?.isEmpty() != true) {
                emit(Resource.Success<Boolean>(true))
            } else {
                emit(Resource.Success<Boolean>(false))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "ERROR"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Cringe no internet connection"))
        }
    }
}
