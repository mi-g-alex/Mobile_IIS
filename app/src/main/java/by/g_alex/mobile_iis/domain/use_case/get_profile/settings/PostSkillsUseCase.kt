package by.g_alex.mobile_iis.domain.use_case.get_profile.settings

import android.util.Log
import by.g_alex.mobile_iis.domain.model.profile.Skill
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import javax.inject.Inject

class PostSkillsUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    suspend fun postSkills(skills: List<Skill>) {
        try {
            val cookie = db_repository.getCookie()
            if (cookie != null) {
                api_repository.postSkills(cookie,skills)
            }

        } catch (e: Exception) {
            Log.e("~~~", e.toString())
        }
    }
}