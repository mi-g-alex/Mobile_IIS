package by.g_alex.mobile_iis.presentation.rating_screen.states

import by.g_alex.mobile_iis.data.remote.dto.faculties.FacultiesDto

data class FacultiesState(
    val isLoading: Boolean = false,
    val FacultiesState: List<FacultiesDto>? = emptyList(),
    val error: String = ""
)
