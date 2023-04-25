package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_email

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.GetCodeUseCase
import by.g_alex.mobile_iis.domain.use_case.login.RestorePasswordApplyUseCase
import by.g_alex.mobile_iis.domain.use_case.login.RestorePasswordEnterLoginUseCase
import by.g_alex.mobile_iis.domain.use_case.login.RestorePasswordGetCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChangeEmailViewModel @Inject constructor(
    private val getCodeUseCase: GetCodeUseCase,
) : ViewModel() {

    private val _state =
        mutableStateOf<ChangeEmailState>(ChangeEmailState())
    val state: State<ChangeEmailState> = _state

    fun editEmailGetCode(id: Int, contactValue: String) {
        getCodeUseCase(id, contactValue).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    //_state.value = RestorePasswordEndState(information = result.data)
                }

                is Resource.Error -> {
                    _state.value = ChangeEmailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = ChangeEmailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

/*    fun restorePasswordApply(login: String, password:String, contactValue: String, code: String) {
        restorePasswordApplyUseCase(login, password, contactValue, code).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ChangeEmailState(information = result.data)
                }

                is Resource.Error -> {
                    _state.value = ChangeEmailState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }

                is Resource.Loading -> {
                    _state.value = ChangeEmailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }*/
}