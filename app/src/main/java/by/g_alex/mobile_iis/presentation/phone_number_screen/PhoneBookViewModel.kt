package by.g_alex.mobile_iis.presentation.phone_number_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.phone_book.PhoneSearchDto
import by.g_alex.mobile_iis.data.remote.dto.phone_book.RequestDto
import by.g_alex.mobile_iis.domain.use_case.phone_book.getPhoneBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class PhoneBookViewModel @Inject constructor(
    private val getPhoneBookUseCase: getPhoneBookUseCase
) : ViewModel() {

    private val _state = mutableStateOf<PhoneState>(PhoneState())
    val state: State<PhoneState> = _state

    val pageList = mutableListOf<PhoneSearchDto>()
//    init {
//        getPhoneBook()
//    }

    fun getPhoneBook(value:RequestDto) {
        getPhoneBookUseCase(value) .onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = PhoneState(PhoneState = result.data)
                    pageList.add(result.data!!)
                }

                is Resource.Loading -> {
                    _state.value = PhoneState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = PhoneState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}