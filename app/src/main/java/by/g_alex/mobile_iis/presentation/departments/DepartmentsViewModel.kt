package by.g_alex.mobile_iis.presentation.departments

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentsTreeDto
import by.g_alex.mobile_iis.domain.use_case.departments_list.GetDepartmentsTreeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DepartmentsViewModel @Inject constructor(
    private val getDepartmentsTreeUseCase: GetDepartmentsTreeUseCase
) : ViewModel() {

    private val _state = mutableStateOf<DepartmentsState>(DepartmentsState())
    val state: State<DepartmentsState> = _state

    private val _list = mutableListOf<DepartmentsTreeDto.Data>()

    init {
        getDepartments()
    }

    private fun getDepartments() {
        getDepartmentsTreeUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.let { setList(it) }
                    _state.value = DepartmentsState(departmentState = _list)
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

    private fun setList(a : List<DepartmentsTreeDto> ) {
        for(i in a) {
            i.data?.let { _list.add(it) }
            if(i.children != null) setList(i.children)
        }
    }
}