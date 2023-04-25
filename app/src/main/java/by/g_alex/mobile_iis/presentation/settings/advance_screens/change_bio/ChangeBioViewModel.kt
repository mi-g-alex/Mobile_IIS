package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_bio

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.PutSummaryUseCase
import by.g_alex.mobile_iis.domain.use_case.login.RestorePasswordCheckExistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeBioViewModel @Inject constructor(
    private val putSummaryUseCase: PutSummaryUseCase,
    private val db_repository: UserDataBaseRepository,
) : ViewModel() {

    private val _state =
        mutableStateOf<ChangeBioSelectState>(ChangeBioSelectState())
    val state: State<ChangeBioSelectState> = _state

    suspend fun changeBio(bio : String) {
        val cvDto = db_repository.getProfilePersonalCV()
        cvDto?.summary = bio;
        if (cvDto != null) {
            putSummaryUseCase.putSummary(cvDto).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = ChangeBioSelectState(information = result.data)
                    }

                    is Resource.Error -> {
                        _state.value = ChangeBioSelectState(
                            error = result.message ?: "An unexpected error occured"
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = ChangeBioSelectState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun changeBio1(bio: String) {
        viewModelScope.launch {
            changeBio(bio)
        }
    }
}