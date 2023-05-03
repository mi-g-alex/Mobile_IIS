package by.g_alex.mobile_iis.presentation.diploma_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.diploma.GetDiplomasUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.diploma.GetPracticeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DiplomaViewModel @Inject constructor(
    private val getPracticeUseCase: GetPracticeUseCase,
    private val getDiplomasUseCase: GetDiplomasUseCase
) : ViewModel() {

    private val _state = mutableStateOf<DiplomaState>(DiplomaState())
    val state: State<DiplomaState> = _state

    private val pr_state = mutableStateOf<PracticeState>(PracticeState())
    val prState: State<PracticeState> = pr_state

    init {
        getPractice()
        getDiploma()
    }

    private fun getDiploma() {
        getDiplomasUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DiplomaState(diplomaState = result.data ?: emptyList())
                }

                is Resource.Loading -> {
                    _state.value = DiplomaState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = DiplomaState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPractice() {
        getPracticeUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    pr_state.value = PracticeState(practice = result.data ?: emptyList())
                }

                is Resource.Loading -> {
                    pr_state.value = PracticeState(isLoading = true)
                }

                is Resource.Error -> {
                    pr_state.value = PracticeState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}