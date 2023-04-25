package by.g_alex.mobile_iis.presentation.settings

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.GetSettingsUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.PutJobUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.PutPublishedUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.settings.PutRatingUseCase
import by.g_alex.mobile_iis.domain.use_case.login.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase,
    private val putPublishedUseCase: PutPublishedUseCase,
    private val putJobUseCase: PutJobUseCase,
    private val putRatingUseCase: PutRatingUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val db: UserDataBaseRepository
) : ViewModel() {

    private val _state = mutableStateOf<SettingsState>(SettingsState())

    val state: State<SettingsState> = _state

    val jobCheck = mutableStateOf(false)
    val pubCheck = mutableStateOf(false)
    val rateCheck = mutableStateOf(false)

    init {
        getEmail()
        viewModelScope.launch {
            val CV = db.getProfilePersonalCV()
            jobCheck.value = CV?.searchJob ?: false
            pubCheck.value = CV?.published ?: false
            rateCheck.value = CV?.showRating ?: false

        }
    }

    private fun getEmail() {
        getSettingsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = SettingsState(contacts = result.data)
                }
                is Resource.Loading -> {
                    _state.value = SettingsState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = SettingsState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
    fun putSearchJob(){
        viewModelScope.launch {
            val cvDto = db.getProfilePersonalCV()
            if(cvDto!=null) {
                Log.e("sdsd","dscsd")
                cvDto.searchJob = !cvDto.searchJob!!
                putJobUseCase.putJob(cvDto)
                db.setProfilePersonalCV(cvDto)
                jobCheck.value = cvDto.searchJob!!
            }
        }
    }
    fun putRating(){
        viewModelScope.launch {
            val cvDto = db.getProfilePersonalCV()
            if(cvDto!=null) {
                Log.e("sdsd","dscsd")
                cvDto.showRating = !cvDto.showRating!!
                putRatingUseCase.putRating(cvDto)
                db.setProfilePersonalCV(cvDto)
                rateCheck.value = cvDto.showRating!!
            }
        }
    }
    fun putPublished(){
        viewModelScope.launch {
            val cvDto = db.getProfilePersonalCV()
            if(cvDto!=null) {
                Log.e("sdsd","dscsd")
                cvDto.published = !cvDto.published!!
                putPublishedUseCase.putPublished(cvDto)
                db.setProfilePersonalCV(cvDto)
                pubCheck.value = cvDto.published!!
            }
        }
    }
    fun logOut() {
        viewModelScope.launch {
            logOutUseCase.logOut()
        }
    }
}