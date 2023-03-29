package by.g_alex.mobile_iis.presentation.schedule.states

import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel

data class EmployeeState(
    val isLoading: Boolean = false,
    var preps : List<EmployeeModel>? = emptyList(),
    val error:String = ""
)
