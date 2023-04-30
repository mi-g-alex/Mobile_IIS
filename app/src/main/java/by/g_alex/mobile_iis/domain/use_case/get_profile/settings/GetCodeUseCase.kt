package by.g_alex.mobile_iis.domain.use_case.get_profile.settings

import android.util.Log
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.settings.EmailChangeDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCodeUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(id: Int, value: String): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<Int>("LessCookie"))
            }
            if (cookie != null) {
                val response = api_repository.updateEmail(cookie, EmailChangeDto(id, value))
                Log.e("~~~", "Update | ${response?.string().toString()}")
                if (response?.string()?.isEmpty() == true) {
                    val response1 = api_repository.getCodeForEmail(cookie, id)
                    Log.e("~~~", "Message send | ${response1?.string().toString()}")
                    emit(Resource.Success<Int>(228))
                } else {
                    emit(Resource.Error<Int>("Error"))
                }
            }
        } catch (e: HttpException) {
            Log.e("~~~", "|HttpException|GetCode ${e.toString()}")
        } catch (_: IOException) {
            Log.e("~~~", "IoException")
        } catch (_: Exception) {
            Log.e("~~~", "Other Ex")
        }
    }
}