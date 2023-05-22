package by.g_alex.mobile_iis.presentation.user_group

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.get_profile.user_group.GetUserGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserGroupViewModel @Inject constructor(
    private val getUserGroupUseCase: GetUserGroupUseCase,
    private val db: UserDataBaseRepository
) : ViewModel() {

    private val _state = mutableStateOf<UserGroupState>(UserGroupState())
    val state: State<UserGroupState> = _state

    init {
        getUserGroup()
    }

    private fun getUserGroup() {
        viewModelScope.launch {

        }
        getUserGroupUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = UserGroupState(userGroupState = result.data)
                }

                is Resource.Loading -> {
                    _state.value = UserGroupState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = UserGroupState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}