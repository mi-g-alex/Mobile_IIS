package by.g_alex.mobile_iis.presentation.dormitory_screen

import by.g_alex.mobile_iis.data.local.entity.DormitoryDto

data class DormitoryState (
    val isLoading: Boolean = false,
    val dormState: List<DormitoryDto> = emptyList(),
    val error: String = ""
)