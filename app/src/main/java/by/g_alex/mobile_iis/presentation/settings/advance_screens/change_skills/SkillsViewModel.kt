package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_skills

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.domain.model.profile.Skill
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.PostSkillsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val skillsUseCase: PostSkillsUseCase,
    private val db_repository: UserDataBaseRepository,
) : ViewModel() {

    suspend fun getSkillFromDB():List<Skill>{
        return db_repository.getProfilePersonalCV()?.skills?: emptyList()
    }
    fun putSkills(refs:List<Skill>){
        viewModelScope.launch {
            skillsUseCase.postSkills(refs)
            val cv = db_repository.getProfilePersonalCV()
            cv?.skills = refs
            db_repository.setProfilePersonalCV(cv!!)
        }
    }

}