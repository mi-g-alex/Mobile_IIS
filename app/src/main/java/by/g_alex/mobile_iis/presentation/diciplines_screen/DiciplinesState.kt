package by.g_alex.mobile_iis.presentation.diciplines_screen

import by.g_alex.mobile_iis.data.remote.dto.diciplines.DiciplinesDto

data class DiciplinesState(
    val isLoading: Boolean = false,
    val diciplineState: List<DiciplinesDto>? = emptyList(),
    val error: String = ""
)