package by.g_alex.mobile_iis.presentation.rating_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.rating_use_cases.GetFacultiesUseCase
import by.g_alex.mobile_iis.domain.use_case.rating_use_cases.GetRatingUseCase
import by.g_alex.mobile_iis.domain.use_case.rating_use_cases.GetSpecialitiesUseCase
import by.g_alex.mobile_iis.presentation.rating_screen.states.FacultiesState
import by.g_alex.mobile_iis.presentation.rating_screen.states.RatingState
import by.g_alex.mobile_iis.presentation.rating_screen.states.SpecialitiesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RatingAllViewModel @Inject constructor(
    private val getFacultiesUseCase: GetFacultiesUseCase,
    private val getSpecialitiesUseCase: GetSpecialitiesUseCase,
    private val getRatingUseCase: GetRatingUseCase,
) : ViewModel() {

    private val _state = mutableStateOf<FacultiesState>(FacultiesState())
    private val sp_state = mutableStateOf<SpecialitiesState>(SpecialitiesState())
    private val r_state = mutableStateOf<RatingState>(RatingState())
    val state: State<FacultiesState> = _state
    val spState: State<SpecialitiesState> = sp_state
    val rState: State<RatingState> = r_state

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
    fun getSpecialities(id:Int,year:Int){
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
    fun getRating(year: Int,id: Int){
        getRatingUseCase(id = id, year = year).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    r_state.value = RatingState(RatingState = result.data ?: emptyList())
                    Log.e("TAgg",r_state.value.RatingState.toString())
                }
                is Resource.Loading -> {
                    r_state.value = RatingState(isLoading = true)
                }
                is Resource.Error -> {
                    r_state.value = RatingState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}