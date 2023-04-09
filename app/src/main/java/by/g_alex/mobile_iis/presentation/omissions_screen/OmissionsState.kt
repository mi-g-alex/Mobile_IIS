package by.g_alex.mobile_iis.presentation.omissions_screen

import by.g_alex.mobile_iis.data.remote.dto.omissions.OmissionsByStudentDto

data class OmissionsState (
    val isLoading: Boolean = false,
    val omissionsState: List<OmissionsByStudentDto>? = null,
    val error: String = ""
)