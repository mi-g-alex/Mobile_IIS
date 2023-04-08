package by.g_alex.mobile_iis.domain.use_case.get_profile.personal_cv

import android.util.Log
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.awaitResponse
import java.io.File
import javax.inject.Inject


class UpdatePhotoUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    suspend fun updatePhoto(data : String) {
        try {
            val data1 = "data:image/jpeg;base64,$data"
            Log.e("~~~", data1)
            val cookie = db_repository.getCookie()
            if (cookie != null) {
                Log.e("~~~", cookie)
                val responseFromLogin =
                    api_repository.updatePhoto(data1, cookie).awaitResponse()
                Log.e("~~~", responseFromLogin.toString())
            }
        } catch (e: Exception) {
            Log.e("~~~", e.toString())
        }
    }
}