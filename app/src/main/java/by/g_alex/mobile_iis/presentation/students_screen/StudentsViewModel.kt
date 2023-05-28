package by.g_alex.mobile_iis.presentation.students_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.students.StudentResponseDto
import by.g_alex.mobile_iis.data.remote.dto.students.StudentsRequestDto
import by.g_alex.mobile_iis.domain.use_case.rating_use_cases.GetFacultiesUseCase
import by.g_alex.mobile_iis.domain.use_case.students_use_case.GetStudentProfileUseCase
import by.g_alex.mobile_iis.presentation.rating_screen.states.FacultiesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel@Inject constructor(
    private val getFacultiesUseCase: GetFacultiesUseCase,
    private val getStudentProfileUseCase: GetStudentProfileUseCase
) : ViewModel() {
    private val _state = mutableStateOf<FacultiesState>(FacultiesState())
    val state: State<FacultiesState> = _state
    private val _Sstate = mutableStateOf<StudentsState>(StudentsState())
    val Sstate: State<StudentsState> = _Sstate

    val pageList = mutableListOf<StudentResponseDto>()

    val expandedFaculties = mutableStateOf(false)
    val selectedFacultiesText = mutableStateOf("")

    val expandedYears = mutableStateOf(false)
    val selectedYearsText = mutableStateOf("")

    init {
        getFaculties()
    }
    fun getprofiles(value:StudentsRequestDto){
        getStudentProfileUseCase(value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _Sstate.value = StudentsState(StudentsState = result.data)
                    pageList.add(result.data!!)
                }
                is Resource.Loading -> {
                    _Sstate.value = StudentsState(isLoading = true)
                }
                is Resource.Error -> {
                    _Sstate.value = StudentsState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
    fun getFaculties() {
        getFacultiesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = FacultiesState(FacultiesState = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = FacultiesState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = FacultiesState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}