package by.g_alex.mobile_iis.presentation.departments.employees_list

import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentEmployeesDto

data class DepartmentsEmployeesListState(
    val isLoading: Boolean = false,
    val employeesState: List<DepartmentEmployeesDto>? = emptyList(),
    val error: String = ""
)
