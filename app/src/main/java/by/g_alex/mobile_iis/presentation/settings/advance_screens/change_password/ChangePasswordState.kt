package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_password

data class ChangePasswordState(
    val isLoading: Boolean = false,
    var information: Any? = null,
    val error: String = ""
)