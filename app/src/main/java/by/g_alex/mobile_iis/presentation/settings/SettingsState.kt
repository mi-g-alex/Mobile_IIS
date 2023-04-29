package by.g_alex.mobile_iis.presentation.settings

import by.g_alex.mobile_iis.data.remote.dto.settings.ContactsDto
import by.g_alex.mobile_iis.domain.model.profile.Reference

data class SettingsState(
    val isLoading: Boolean = false,
    val contacts: ContactsDto? = null,
    val error: String = ""
)