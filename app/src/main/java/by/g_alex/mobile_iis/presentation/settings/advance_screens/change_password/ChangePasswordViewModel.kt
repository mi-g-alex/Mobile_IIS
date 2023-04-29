package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.ChangePasswordUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.PutSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val db_repository: UserDataBaseRepository,
) : ViewModel() {

    private val _state =
        mutableStateOf<ChangePasswordState>(ChangePasswordState())
    val state: State<ChangePasswordState> = _state

    suspend fun changePass1(pass: String, newPass: String) {
        if (pass == db_repository.getLoginAndPassword()?.password) {
            changePasswordUseCase(pass, newPass).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        db_repository.getLoginAndPassword()?.login?.let {
                            db_repository.setLoginAndPassword(
                                it, newPass)
                        }
                        _state.value = ChangePasswordState(information = result.data)
                    }

                    is Resource.Error -> {
                        _state.value = ChangePasswordState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = ChangePasswordState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        } else {
            _state.value = ChangePasswordState(information = 409)
        }
    }

    fun changePass(pass: String, newPass: String) {
        viewModelScope.launch {
            changePass1(pass, newPass)
        }
    }
}