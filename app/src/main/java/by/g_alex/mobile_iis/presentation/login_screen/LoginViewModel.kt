package by.g_alex.mobile_iis.presentation.login_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.domain.use_case.login.LoginToAccountUseCase
import by.g_alex.mobile_iis.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginToAccountUseCase: LoginToAccountUseCase
) : ViewModel() {

    private val _state = mutableStateOf<LoginState>(LoginState())
    val state : State<LoginState> = _state

    fun loginToAccount(username : String, password : String) {
        loginToAccountUseCase(username, password).onEach {result ->
           when(result) {
                is Resource.Success -> {
                    _state.value = LoginState(loginReturn = result.data?.body())
                    _state.value = LoginState(cookie = result.data?.headers()!!["Set-Cookie"].toString())
                }
                is Resource.Error -> {
                    _state.value = LoginState (
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}