package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore

import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto

data class RestorePasswordSelectState(
    val isLoading: Boolean = false,
    val information: RestorePasswordEnterLoginResponseDto? = null,
    val error: String = ""
)