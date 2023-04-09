package by.g_alex.mobile_iis.presentation.profile_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.personal_cv.GetPersonalCVUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.personal_cv.UpdatePhotoUseCase
import by.g_alex.mobile_iis.domain.use_case.login.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileCVUseCase: GetPersonalCVUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val updatePhotoUseCase: UpdatePhotoUseCase
) : ViewModel() {

    private val _state = mutableStateOf<ProfileCVState>(ProfileCVState())
    val state: State<ProfileCVState> = _state

    init {
        getProfileCV()
    }

    private fun getProfileCV() {
        getProfileCVUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProfileCVState(profileInfo = result.data)
                }
                is Resource.Loading -> {
                    _state.value = ProfileCVState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = ProfileCVState(
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

    fun updatePhoto(source: String) {
        viewModelScope.launch {
            updatePhotoUseCase.updatePhoto(source)
        }
    }
}