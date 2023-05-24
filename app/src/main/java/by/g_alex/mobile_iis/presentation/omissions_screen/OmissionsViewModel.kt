package by.g_alex.mobile_iis.presentation.omissions_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.omissions.GetOmissionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OmissionsViewModel @Inject constructor(
    private val getOmissionsUseCase: GetOmissionsUseCase,
) : ViewModel() {

    private val _state = mutableStateOf<OmissionsState>(OmissionsState())
    val state: State<OmissionsState> = _state

    init {
        getOmissions()
    }

    private fun getOmissions() {
        getOmissionsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = OmissionsState(omissionsState = result.data)
                }

                is Resource.Loading -> {
                    _state.value = OmissionsState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = OmissionsState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}