package by.g_alex.mobile_iis.presentation.diciplines_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_diciplines_use_case.GetDiciplinesUseCase
import by.g_alex.mobile_iis.domain.use_case.rating_use_cases.GetFacultiesUseCase
import by.g_alex.mobile_iis.domain.use_case.rating_use_cases.GetSpecialitiesUseCase
import by.g_alex.mobile_iis.presentation.rating_screen.states.FacultiesState
import by.g_alex.mobile_iis.presentation.rating_screen.states.SpecialitiesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DiciplinesViewModel @Inject constructor(
    private val getFacultiesUseCase: GetFacultiesUseCase,
    private val getSpecialitiesUseCase: GetSpecialitiesUseCase,
    private val getDiciplinesUseCase: GetDiciplinesUseCase
) : ViewModel() {

    private val _state = mutableStateOf<FacultiesState>(FacultiesState())
    private val sp_state = mutableStateOf<SpecialitiesState>(SpecialitiesState())
    private val _disState = mutableStateOf<DiciplinesState>(DiciplinesState())

    val state: State<FacultiesState> = _state
    val spState: State<SpecialitiesState> = sp_state
    val disState: State<DiciplinesState> = _disState



    val expandedYears = mutableStateOf(false)
    val selectedYearsText = mutableStateOf("2022")

    val expandedFaculties = mutableStateOf(false)
    val selectedFacultiesText = mutableStateOf("")

    val expandedSpecialities = mutableStateOf(false)
    val selectedSpecialityText = mutableStateOf("")


    init {
        getFaculties()
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

    fun getSpecialities(id: Int, year: Int) {
        getSpecialitiesUseCase(id = id, year).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    sp_state.value = SpecialitiesState(SpecialState = result.data ?: emptyList())
                }

                is Resource.Loading -> {
                    sp_state.value = SpecialitiesState(isLoading = true)
                }

                is Resource.Error -> {
                    sp_state.value = SpecialitiesState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
    fun getDiciplines(id:Int,year: Int){
        getDiciplinesUseCase(id = id, year).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _disState.value = DiciplinesState(diciplineState = result.data ?: emptyList())
                }

                is Resource.Loading -> {
                    _disState.value = DiciplinesState(isLoading = true)
                }

                is Resource.Error -> {
                    _disState.value = DiciplinesState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}