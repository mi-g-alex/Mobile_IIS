package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_email

import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto

data class ChangeEmailState(
    val isLoading: Boolean = false,
    val information: Int? = null,
    val error: String = ""
)