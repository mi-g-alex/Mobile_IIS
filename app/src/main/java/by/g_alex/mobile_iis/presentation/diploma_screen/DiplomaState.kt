package by.g_alex.mobile_iis.presentation.diploma_screen

import by.g_alex.mobile_iis.data.remote.dto.diploma.DiplomaDto
import by.g_alex.mobile_iis.data.remote.dto.dormitory.DormitoryDto

data class DiplomaState (
    val isLoading: Boolean = false,
    val diplomaState: List<DiplomaDto>? = null,
    val error: String = ""
)