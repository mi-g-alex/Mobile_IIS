package by.g_alex.mobile_iis.presentation.study_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.user_group.GetUserGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val getUserGroupUseCase: GetUserGroupUseCase
) : ViewModel() {

    private val _state = mutableStateOf<StudyState>(StudyState())
    val state: State<StudyState> = _state

    init {
        getUserGroup()
    }

    private fun getUserGroup() {
        getUserGroupUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = StudyState(userGroupState = result.data)
                }

                is Resource.Loading -> {
                    _state.value = StudyState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = StudyState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}