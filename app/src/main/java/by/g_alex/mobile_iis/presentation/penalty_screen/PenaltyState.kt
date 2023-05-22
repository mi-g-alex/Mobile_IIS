package by.g_alex.mobile_iis.presentation.penalty_screen

import by.g_alex.mobile_iis.data.local.entity.PenaltyModel

data class PenaltyState (
    val isLoading: Boolean = false,
    val PenaltyState: List<PenaltyModel> = emptyList(),
    val error: String = ""
)