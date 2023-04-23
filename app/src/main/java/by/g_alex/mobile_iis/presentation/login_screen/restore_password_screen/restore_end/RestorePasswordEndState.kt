package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.restore_end

import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto

data class RestorePasswordEndState(
    val isLoading: Boolean = false,
    val information: Boolean? = null,
    val error: String = ""
)