package by.g_alex.mobile_iis.domain.use_case.get_profile.penalty

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.local.entity.PenaltyModel
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPenaltyUseCase@Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(): Flow<Resource<List<PenaltyModel>>> = flow {
        try {
            emit(Resource.Loading<List<PenaltyModel>>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<List<PenaltyModel>>("LessCookie"))
            }
            if (cookie != null) {
                val data = api_repository.getPenalty(cookie)
                emit(Resource.Success<List<PenaltyModel>>(data))
            }
        } catch (e: HttpException) {

        } catch (_: IOException) {

        }
    }
}