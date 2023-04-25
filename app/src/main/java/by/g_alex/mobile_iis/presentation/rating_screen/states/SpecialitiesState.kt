package by.g_alex.mobile_iis.presentation.rating_screen.states

import by.g_alex.mobile_iis.data.remote.dto.specialities.SpecialityDto

data class SpecialitiesState(
    val isLoading: Boolean = false,
    val SpecialState: List<SpecialityDto>? = emptyList(),
    val error: String = ""
)
