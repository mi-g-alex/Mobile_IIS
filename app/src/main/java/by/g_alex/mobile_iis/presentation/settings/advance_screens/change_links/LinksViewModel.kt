package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_links

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.domain.model.profile.Reference
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.PutLinksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinksViewModel @Inject constructor(
    private val putLinksUseCase: PutLinksUseCase,
    private val db_repository: UserDataBaseRepository,
) : ViewModel() {
    suspend fun getLinkFromDB():List<Reference>{
        return db_repository.getProfilePersonalCV()?.references?: emptyList()
    }
    fun putLinks(refs:List<Reference>){
        viewModelScope.launch {
            putLinksUseCase.putLinks(refs)
            val cv = db_repository.getProfilePersonalCV()
            cv?.references = refs
            db_repository.setProfilePersonalCV(cv!!)
        }
    }

}