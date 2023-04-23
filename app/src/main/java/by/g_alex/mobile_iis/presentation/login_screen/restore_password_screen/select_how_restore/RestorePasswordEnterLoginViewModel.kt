package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.login.RestorePasswordEnterLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RestorePasswordEnterLoginViewModel @Inject constructor(
    private val restorePasswordEnterLoginUseCase: RestorePasswordEnterLoginUseCase
) : ViewModel() {

    private val _state =
        mutableStateOf<RestorePasswordEnterLoginState>(RestorePasswordEnterLoginState())
    val state: State<RestorePasswordEnterLoginState> = _state

    fun restorePasswordEnterLogin(username: String) {
        restorePasswordEnterLoginUseCase(username).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RestorePasswordEnterLoginState(information = result.data)
                }

                is Resource.Error -> {
                    _state.value = RestorePasswordEnterLoginState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = RestorePasswordEnterLoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}