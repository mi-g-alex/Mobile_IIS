package by.g_alex.mobile_iis.presentation.dormitory_screen

import by.g_alex.mobile_iis.data.remote.dto.dormitory.DormitoryDto

data class DormitoryState (
    val isLoading: Boolean = false,
    val dormState: List<DormitoryDto>? = null,
    val error: String = ""
)