package by.g_alex.mobile_iis.presentation.penalty_screen

import by.g_alex.mobile_iis.domain.model.profile.penalty_model.PenaltyModel

data class PenaltyState (
    val isLoading: Boolean = false,
    val PenaltyState: List<PenaltyModel>? = null,
    val error: String = ""
)