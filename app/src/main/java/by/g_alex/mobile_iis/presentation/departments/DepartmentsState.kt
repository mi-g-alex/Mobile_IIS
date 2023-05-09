package by.g_alex.mobile_iis.presentation.departments

import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentsTreeDto

data class DepartmentsState(
    val isLoading: Boolean = false,
    val departmentState: List<DepartmentsTreeDto.Data>? = emptyList(),
    val error: String = ""
)
