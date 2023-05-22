package by.g_alex.mobile_iis.presentation.diploma_screen

import by.g_alex.mobile_iis.data.remote.dto.diploma.DiplomaDto

data class DiplomaState (
    val isLoading: Boolean = false,
    val diplomaState: List<DiplomaDto>? = null,
    val error: String = ""
)