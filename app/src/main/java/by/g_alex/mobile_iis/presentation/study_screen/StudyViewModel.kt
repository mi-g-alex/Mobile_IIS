package by.g_alex.mobile_iis.presentation.study_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.use_case.get_profile.study.CloseCertificateUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.study.GetStudyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StudyViewModel @Inject constructor(
    private val getStudyUseCase: GetStudyUseCase,
    private val closeCertificateUseCase: CloseCertificateUseCase
) : ViewModel() {

    private val _state = mutableStateOf<StudyState>(StudyState())
    val state: State<StudyState> = _state

    val needUpdate = mutableStateOf<Boolean>(false)
    val needShowError = mutableStateOf<String?>(null)

    init {
        getStudy()
    }

    private fun getStudy() {
        getStudyUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = StudyState(
                        studyAll = result.data
                    )
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

    fun closeCert(id : Int) {
        Log.e("~~~", "отмена")
        closeCertificateUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    needUpdate.value = true
                }

                is Resource.Loading -> {
                }

                is Resource.Error -> {
                    needShowError.value = "Ошибка отмены"
                }
            }
        }.launchIn(viewModelScope)
    }
}