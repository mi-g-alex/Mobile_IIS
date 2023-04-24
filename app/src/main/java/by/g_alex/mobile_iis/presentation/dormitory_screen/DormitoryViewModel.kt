package by.g_alex.mobile_iis.presentation.dormitory_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.dormitory.GetDormitoryUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.dormitory.GetPrivilegesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DormitoryViewModel @Inject constructor(
    private val getDormitoryUseCase: GetDormitoryUseCase,
    private val getPrivilegesUseCase: GetPrivilegesUseCase
) : ViewModel() {

    private val _state = mutableStateOf<DormitoryState>(DormitoryState())
    val state: State<DormitoryState> = _state

    private val pr_state = mutableStateOf<PrivilegesState>(PrivilegesState())
    val prState: State<PrivilegesState> = pr_state

    init {
        getDormitory()
        getPrivileges()
    }

    fun getDormitory() {
        getDormitoryUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DormitoryState(dormState = result.data ?: emptyList())
                }

                is Resource.Loading -> {
                    _state.value = DormitoryState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = DormitoryState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getPrivileges() {
        getPrivilegesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    pr_state.value = PrivilegesState(privilegeState = result.data ?: emptyList())
                }

                is Resource.Loading -> {
                    pr_state.value = PrivilegesState(isLoading = true)
                }

                is Resource.Error -> {
                    pr_state.value = PrivilegesState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}