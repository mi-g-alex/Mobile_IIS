package by.g_alex.mobile_iis.presentation.departments.employee_info

import by.g_alex.mobile_iis.data.remote.dto.departments.EmployeeDetailInfoDto

data class DepartmentsEmployeesInfoState(
    val isLoading: Boolean = false,
    val employeesState: EmployeeDetailInfoDto? = null,
    val error: String = ""
)
