package by.g_alex.mobile_iis.presentation.department_schedule_screen

import by.g_alex.mobile_iis.data.remote.dto.department.DepartmentDto

data class DepartmentsState(
    val isLoading: Boolean = false,
    val departmentState: List<DepartmentDto>? = emptyList(),
    val error: String = ""
)
