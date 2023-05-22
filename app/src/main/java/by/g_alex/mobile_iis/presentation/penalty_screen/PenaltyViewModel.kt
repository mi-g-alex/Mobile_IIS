package by.g_alex.mobile_iis.presentation.penalty_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.local.entity.PenaltyModel
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.get_profile.penalty.GetPenaltyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PenaltyViewModel @Inject constructor(
    private val getPenaltyUseCase: GetPenaltyUseCase,
    private val db: UserDataBaseRepository
) : ViewModel() {

    private val _state = mutableStateOf<PenaltyState>(PenaltyState())
    val state: State<PenaltyState> = _state

    init {
        getPenalty()
    }

    private fun getPenalty() {
        viewModelScope.launch {
            val penaltys: List<PenaltyModel> = db.getPenalty()
            if (penaltys.isNotEmpty())
                _state.value = PenaltyState(PenaltyState = penaltys)
        }
        getPenaltyUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PenaltyState(PenaltyState = result.data?: emptyList())
                    db.deletePenalty()
                    for (n in _state.value.PenaltyState?: emptyList()) {
                        db.insertPenalty(n)
                    }
                }

                is Resource.Loading -> {
                    //_state.value = PenaltyState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = PenaltyState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}