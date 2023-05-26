package by.g_alex.mobile_iis.presentation.bug_report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.feedback.SendFeedbackUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BugRepostViewModel @Inject constructor(
    private val sendFeedbackUseCase: SendFeedbackUseCase
) : ViewModel() {

    val text = mutableStateOf("")
    val link = mutableStateOf<String>("")

    val needShowThanks = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val needShowErr = mutableStateOf(false)

    fun sendFeedback(text: String, link: String?) {
        sendFeedbackUseCase(text, link).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    isLoading.value = false
                    needShowThanks.value = true
                    needShowErr.value = false
                }

                is Resource.Loading -> {
                    isLoading.value = true
                    needShowThanks.value = false
                    needShowErr.value = false
                }

                is Resource.Error -> {
                    isLoading.value = false
                    needShowThanks.value = false
                    needShowErr.value = true
                }
            }
        }.launchIn(viewModelScope)
    }
}