package by.g_alex.mobile_iis.domain.use_case.get_profile.dormitory

import android.util.Log
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.dormitory.PrivilegesDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
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
                Log.e("DATATATA",data.toString())
                emit(Resource.Success<List<PrivilegesDto>>(data))
            }
        } catch (e: HttpException) {

        } catch (_: IOException) {

        }
    }
}