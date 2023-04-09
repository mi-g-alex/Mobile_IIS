package by.g_alex.mobile_iis.domain.use_case.get_profile.personal_cv

import android.util.Log
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import retrofit2.awaitResponse
import javax.inject.Inject


class UpdatePhotoUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    suspend fun updatePhoto(data : String) {
        try {
            val data1 = "data:image/jpeg;base64,$data"
            val cookie = db_repository.getCookie()
            if (cookie != null) {
                val responseFromLogin =
                    api_repository.updatePhoto(data1, cookie).awaitResponse()
                Log.e("~~~", responseFromLogin.toString())
            }
        } catch (e: Exception) {
            Log.e("~~~", e.toString())
        }
    }
}