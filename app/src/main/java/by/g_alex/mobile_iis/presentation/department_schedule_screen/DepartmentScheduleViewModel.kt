package by.g_alex.mobile_iis.presentation.department_schedule_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.department_schedule_use_case.GetAnounsmentsByDepartment
import by.g_alex.mobile_iis.domain.use_case.department_schedule_use_case.GetDepartmentsUseCase
import by.g_alex.mobile_iis.presentation.announcement_screen.AnnouncementState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DepartmentScheduleViewModel @Inject constructor(
    private val getDepartmentsUseCase: GetDepartmentsUseCase,
    private val getAnnouncementsByUseCase: GetAnounsmentsByDepartment
) : ViewModel() {

    private val _state = mutableStateOf<DepartmentsState>(DepartmentsState())
    private val _anState = mutableStateOf<AnnouncementState>(AnnouncementState())

    val state: State<DepartmentsState> = _state
    val AnState: State<AnnouncementState> = _anState

    val expanded = mutableStateOf(false)
    val selectedText = mutableStateOf("")

    val selectedId = mutableStateOf(0)

    init {
        getDepartments()
    }


    fun getDepartments() {
        getDepartmentsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DepartmentsState(departmentState = result.data ?: emptyList())
                }

                is Resource.Loading -> {
                    _state.value = DepartmentsState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = DepartmentsState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAnnounsments(id: Int) {
        getAnnouncementsByUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _anState.value = AnnouncementState(anonsState = result.data ?: emptyList())
                }

                is Resource.Loading -> {
                    _anState.value = AnnouncementState(isLoading = true)
                }

                is Resource.Error -> {
                    _anState.value = AnnouncementState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

}