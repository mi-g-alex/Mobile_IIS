package by.g_alex.mobile_iis.domain.use_case.login

import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository : UserDataBaseRepository
) {
    suspend fun logOut(){
        try {
            val cookie = db_repository.getCookie()
            if (cookie != null) {
                api_repository.logout(cookie)
            }
            db_repository.deleteCookie()
            db_repository.deleteLoginAndPassword()
            db_repository.deleteProfilePersonalCV()
        } catch (_: Exception) {}
    }
}

