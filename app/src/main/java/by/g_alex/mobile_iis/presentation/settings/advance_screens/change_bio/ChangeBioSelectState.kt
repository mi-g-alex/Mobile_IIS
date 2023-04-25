package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_bio

import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto

data class ChangeBioSelectState(
    val isLoading: Boolean = false,
    var information: Any? = null,
    val error: String = ""
)