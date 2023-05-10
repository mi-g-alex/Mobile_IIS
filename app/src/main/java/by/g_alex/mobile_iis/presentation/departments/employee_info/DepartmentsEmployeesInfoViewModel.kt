package by.g_alex.mobile_iis.presentation.departments.employee_info

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.departments_list.GetEmployeeDetailsInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DepartmentsEmployeesInfoViewModel @Inject constructor(
    private val getEmployeeDetailsInfoUseCase: GetEmployeeDetailsInfoUseCase
) : ViewModel() {

    private val _state =
        mutableStateOf<DepartmentsEmployeesInfoState>(DepartmentsEmployeesInfoState())
    val state: State<DepartmentsEmployeesInfoState> = _state
    val name = mutableStateOf<String>("")

    fun getEmployees(id: String) {
        getEmployeeDetailsInfoUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DepartmentsEmployeesInfoState(employeesState = result.data)
                }

                is Resource.Loading -> {
                    _state.value = DepartmentsEmployeesInfoState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = DepartmentsEmployeesInfoState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}