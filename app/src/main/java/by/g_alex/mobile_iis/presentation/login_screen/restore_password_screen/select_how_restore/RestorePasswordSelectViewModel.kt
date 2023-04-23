package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.login.RestorePasswordCheckExistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RestorePasswordSelectViewModel @Inject constructor(
    private val restorePasswordCheckExistUseCase: RestorePasswordCheckExistUseCase
) : ViewModel() {

    private val _state =
        mutableStateOf<RestorePasswordSelectState>(RestorePasswordSelectState())
    val state: State<RestorePasswordSelectState> = _state

    fun restorePasswordCheck(login: String, contactValue : String) {
        restorePasswordCheckExistUseCase(login, contactValue).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RestorePasswordSelectState(information = result.data)
                }

                is Resource.Error -> {
                    _state.value = RestorePasswordSelectState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = RestorePasswordSelectState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}