package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.restore_end

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.login.RestorePasswordApplyUseCase
import by.g_alex.mobile_iis.domain.use_case.login.RestorePasswordEnterLoginUseCase
import by.g_alex.mobile_iis.domain.use_case.login.RestorePasswordGetCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RestorePasswordEndViewModel @Inject constructor(
    private val restorePasswordApplyUseCase: RestorePasswordApplyUseCase,
    private val restorePasswordGetCodeUseCase: RestorePasswordGetCodeUseCase
) : ViewModel() {

    private val _state =
        mutableStateOf<RestorePasswordEndState>(RestorePasswordEndState())
    val state: State<RestorePasswordEndState> = _state

    fun restorePasswordGetCode(login: String, contactValue: String) {
        restorePasswordGetCodeUseCase(login, contactValue).onEach { result ->
            when (result) {
                is Resource.Success -> {
                  //  _state.value = RestorePasswordEndState(information = result.data)
                }

                is Resource.Error -> {
                    _state.value = RestorePasswordEndState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = RestorePasswordEndState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun restorePasswordApply(login: String, password:String, contactValue: String, code: String) {
        restorePasswordApplyUseCase(login, password, contactValue, code).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RestorePasswordEndState(information = result.data)
                }

                is Resource.Error -> {
                    _state.value = RestorePasswordEndState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = RestorePasswordEndState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}