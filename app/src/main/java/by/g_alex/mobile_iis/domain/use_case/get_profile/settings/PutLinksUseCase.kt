package by.g_alex.mobile_iis.domain.use_case.get_profile.settings

import android.util.Log
import by.g_alex.mobile_iis.domain.model.profile.Reference
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import javax.inject.Inject

class PutLinksUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    suspend fun putLinks(refs: List<Reference>) {
        try {
            val cookie = db_repository.getCookie()
            if (cookie != null) {
                api_repository.putLinks(cookie,refs)
            }

        } catch (e: Exception) {
            Log.e("~~~", e.toString())
        }
    }
}