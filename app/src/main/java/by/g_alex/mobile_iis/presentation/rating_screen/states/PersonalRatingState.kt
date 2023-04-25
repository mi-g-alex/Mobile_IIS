package by.g_alex.mobile_iis.presentation.rating_screen.states

import by.g_alex.mobile_iis.data.remote.dto.personal_rating.PersonalRatingDto

data class PersonalRatingState(
    val isLoading: Boolean = false,
    val PersonalState: PersonalRatingDto? = null,
    val error: String = ""
)
