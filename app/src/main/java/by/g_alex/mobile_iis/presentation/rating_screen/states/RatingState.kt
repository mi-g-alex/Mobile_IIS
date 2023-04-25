package by.g_alex.mobile_iis.presentation.rating_screen.states

import by.g_alex.mobile_iis.data.remote.dto.rating.RatingDto

data class RatingState(
    val isLoading: Boolean = false,
    val RatingState: List<RatingDto>? = emptyList(),
    val error: String = ""
)
