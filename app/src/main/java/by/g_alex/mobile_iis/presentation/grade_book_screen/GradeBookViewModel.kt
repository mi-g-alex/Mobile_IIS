package by.g_alex.mobile_iis.presentation.grade_book_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.grade_book.GetGradeBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GradeBookViewModel @Inject constructor(
    private val getGradeBookUseCase: GetGradeBookUseCase
) : ViewModel() {

    private val _state = mutableStateOf<GradeBookState>(GradeBookState())
    val state: State<GradeBookState> = _state

    init {
        getGradeBook()
    }

    fun getGradeBook() {
        getGradeBookUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = GradeBookState(gradeBookState = result.data?: emptyList())
                }

                is Resource.Loading -> {
                    _state.value = GradeBookState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = GradeBookState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}