package by.g_alex.mobile_iis.presentation.announcement_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.announcemnts.GetAnnouncementsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AnnouncementViewModel @Inject constructor(
    private val getAnnouncementsUseCase: GetAnnouncementsUseCase
) : ViewModel() {

    private val _state = mutableStateOf<AnnouncementState>(AnnouncementState())
    val state: State<AnnouncementState> = _state

    init {
        getAnnouncements()
    }

    fun getAnnouncements() {
        getAnnouncementsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AnnouncementState(anonsState = result.data?: emptyList())
                }

                is Resource.Loading -> {
                    _state.value = AnnouncementState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = AnnouncementState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}