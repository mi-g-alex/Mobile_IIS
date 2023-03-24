package by.g_alex.mobile_iis.presentation.login_screen

import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto

data class LoginState(
    val isLoading: Boolean = false,
    val loginReturn: LoginResponseDto? = null,
    val cookie : String? = "",
    val error: String = ""
)