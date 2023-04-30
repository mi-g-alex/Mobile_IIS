package by.g_alex.mobile_iis.domain.use_case.get_profile.settings

import android.util.Log
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(pass: String, newPass: String): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<Int>("LessCookie"))
            }
            if (cookie != null) {
                val response = api_repository.changePass(cookie, pass, newPass)
                if(response?.string()?.isEmpty() == true) {
                        Log.e("~~~", "Confirmed")
                        emit(Resource.Success<Int>(200))
                } else {
                    emit(Resource.Error<Int>("Error"))
                }
            }
        } catch (e: HttpException) {
            Log.e("~~~", "HttpException||||${e.toString()}")
        } catch (_: IOException) {
            Log.e("~~~", "IoException")
        } catch(_ : Exception) {
            Log.e("~~~", "Other Ex")
        }
    }
}