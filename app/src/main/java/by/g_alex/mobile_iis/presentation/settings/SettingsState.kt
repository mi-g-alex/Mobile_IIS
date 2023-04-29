package by.g_alex.mobile_iis.presentation.settings

data class SettingsState(
    val isLoading: Boolean = false,
    val allGood: Boolean? = null,
    val error: String = ""
)