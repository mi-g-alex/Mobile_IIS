package by.g_alex.mobile_iis.presentation.settings.advance_screens.check_email

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.model.profile.Skill
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckEmailViewModel @Inject constructor(
    private val db_repository: UserDataBaseRepository,
) : ViewModel() {

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    suspend fun getEmail() {
        email.value = db_repository.getProfilePersonalCV()?.officeEmail ?: ""
        password.value = db_repository.getProfilePersonalCV()?.officePassword ?: ""
    }

}