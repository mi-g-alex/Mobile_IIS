package by.g_alex.mobile_iis.presentation.mark_book

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.get_personal_cv.GetPersonalCVUseCase
import by.g_alex.mobile_iis.domain.use_case.login.LogOutUseCase
import by.g_alex.mobile_iis.domain.use_case.mark_book.GetMarkBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkBookViewModel @Inject constructor(
    private val getMarkBookUseCase: GetMarkBookUseCase
) : ViewModel() {

    private val _state = mutableStateOf<MarkBookState>(MarkBookState())
    val state: State<MarkBookState> = _state

    init {
        getMarkBook()
    }

    private fun getMarkBook() {
        getMarkBookUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MarkBookState(markBookState = result.data)
                }
                is Resource.Loading -> {
                    _state.value = MarkBookState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = MarkBookState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}