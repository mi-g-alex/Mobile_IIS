package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_email

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.ConfirmEmailCodeUseCase
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
    private val confirmEmailCodeUseCase: ConfirmEmailCodeUseCase
) : ViewModel() {

    private val _state =
        mutableStateOf<ChangeEmailState>(ChangeEmailState())
    val state: State<ChangeEmailState> = _state

    fun editEmailGetCode(id: Int, contactValue: String) {
        getCodeUseCase(id, contactValue).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ChangeEmailState(information = 228)
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

    fun confirmEmailCode(id : Int, code : String) {
        confirmEmailCodeUseCase(id, code).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    Log.e("~~~", result.data.toString() + "!!!!!!!!!!!!!!!!!!!!!!!!!!")
                    _state.value = ChangeEmailState(error = "200")
                    Log.e("~~~", _state.value.information.toString() + "!!!!!!!!!!!!!!!!!!!!")
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
}