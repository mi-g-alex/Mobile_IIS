package by.g_alex.mobile_iis.presentation.settings

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.get_profile.personal_cv.GetPersonalCVUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.personal_cv.UpdatePhotoUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.GetSettingsUseCase
import by.g_alex.mobile_iis.domain.use_case.login.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel() {

    private val _state = mutableStateOf<SettingsState>(SettingsState())
    val state: State<SettingsState> = _state

    init {
        getEmail()
    }

    private fun getEmail() {
        getSettingsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = SettingsState(contacts = result.data)
                }
                is Resource.Loading -> {
                    _state.value = SettingsState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = SettingsState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase.logOut()
        }
    }
}