package by.g_alex.mobile_iis.presentation.mark_book

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.get_profile.mark_book.GetMarkBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarkBookViewModel @Inject constructor(
    private val getMarkBookUseCase: GetMarkBookUseCase,
    private val db: UserDataBaseRepository
) : ViewModel() {

    private val _state = mutableStateOf<MarkBookState>(MarkBookState())
    val state: State<MarkBookState> = _state

    init {
        getMarkBook()
    }

    fun getMarkBook() {
        viewModelScope.launch {
            val markbooks: List<MarkBookMarkModel> = db.getMarkBooks()
            if (markbooks.isNotEmpty())
                _state.value = MarkBookState(markBookState = markbooks)
        }
        getMarkBookUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = MarkBookState(markBookState = result.data)
                    db.deleteMarkBooks()
                    for (n in _state.value.markBookState ?: emptyList()) {
                        db.insertMarkBook(n)
                    }
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