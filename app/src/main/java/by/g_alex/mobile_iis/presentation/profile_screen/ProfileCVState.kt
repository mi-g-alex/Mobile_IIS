package by.g_alex.mobile_iis.presentation.profile_screen

import by.g_alex.mobile_iis.domain.model.profile.PersonalCV

data class ProfileCVState (
    val isLoading: Boolean = false,
    val profileInfo: PersonalCV? = null,
    val error: String = ""
)