package by.g_alex.mobile_iis.presentation.dormitory_screen

import by.g_alex.mobile_iis.data.local.entity.PrivilegesDto

data class PrivilegesState (
    val isLoading: Boolean = false,
    val privilegeState: List<PrivilegesDto> = emptyList(),
    val error: String = ""
)