package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.enter_login

import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto

data class RestorePasswordEnterLoginState(
    val isLoading: Boolean = false,
    val information: RestorePasswordEnterLoginResponseDto? = null,
    val error: String = ""
)