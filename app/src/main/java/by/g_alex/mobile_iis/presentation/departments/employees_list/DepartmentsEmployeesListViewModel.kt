package by.g_alex.mobile_iis.presentation.departments.employees_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.departments_list.GetDepartmentsEmployeesUseCase
import by.g_alex.mobile_iis.domain.use_case.departments_list.GetDepartmentsNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DepartmentsEmployeesListViewModel @Inject constructor(
    private val getDepartmentsEmployeesUseCase: GetDepartmentsEmployeesUseCase
) : ViewModel() {

    private val _state =
        mutableStateOf<DepartmentsEmployeesListState>(DepartmentsEmployeesListState())
    val state: State<DepartmentsEmployeesListState> = _state
    val name = mutableStateOf<String>("")

    fun getEmployees(id: Int) {
        getDepartmentsEmployeesUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DepartmentsEmployeesListState(employeesState = result.data)
                }

                is Resource.Loading -> {
                    _state.value = DepartmentsEmployeesListState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = DepartmentsEmployeesListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}