package by.g_alex.mobile_iis.presentation.diploma_screen

import by.g_alex.mobile_iis.data.remote.dto.diploma.PracticeDto

data class PracticeState (
    val isLoading: Boolean = false,
    val practice: List<PracticeDto>? = null,
    val error: String = ""
)