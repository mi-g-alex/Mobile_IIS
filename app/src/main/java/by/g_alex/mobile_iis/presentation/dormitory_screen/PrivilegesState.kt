package by.g_alex.mobile_iis.presentation.dormitory_screen

import by.g_alex.mobile_iis.data.remote.dto.dormitory.PrivilegesDto

data class PrivilegesState (
    val isLoading: Boolean = false,
    val privilegeState: List<PrivilegesDto>? = null,
    val error: String = ""
)