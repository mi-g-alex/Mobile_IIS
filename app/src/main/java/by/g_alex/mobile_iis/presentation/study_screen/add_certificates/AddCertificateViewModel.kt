package by.g_alex.mobile_iis.presentation.study_screen.add_certificates

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.study.CertificatePlacesDto
import by.g_alex.mobile_iis.data.remote.dto.study.SendCertificateDto
import by.g_alex.mobile_iis.domain.use_case.get_profile.study.GetCertificatePlacesUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.study.SendCertificateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddCertificateViewModel @Inject constructor(
    private val getCertificatePlacesUseCase: GetCertificatePlacesUseCase,
    private val sendCertificateUseCase: SendCertificateUseCase
) : ViewModel() {

    private val _state = mutableStateOf<AddCertificateState>(AddCertificateState())
    val state: State<AddCertificateState> = _state
    private val _sendState = mutableStateOf<SendCertificateState>(SendCertificateState())
    val sendState: State<SendCertificateState> = _sendState

    val expandedPlaces = mutableStateOf(false)
    val selectedPlace = mutableStateOf<CertificatePlacesDto.Place?>(null)
    val selectedText = mutableStateOf("")
    val commentText = mutableStateOf("")

    val isHerb = mutableStateOf(false)

    init {
        getCerts()
    }

    private fun getCerts() {
        getCertificatePlacesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = AddCertificateState(
                        listOfItems = result.data
                    )
                }

                is Resource.Loading -> {
                    _state.value = AddCertificateState(isLoading = true)
                }

                is Resource.Error -> {
                    _state.value = AddCertificateState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun sendCertificate() {
        val typeOfCert: String = (if (isHerb.value) "гербовая" else "обычная")
        val placeOfCert =
            selectedText.value + (if (commentText.value.isNotBlank()) " (" + commentText.value + ")" else "")
        val resp = SendCertificateDto(
            certificateCount = 1,
            certificateRequestDto = SendCertificateDto.CertificateRequestDto(
                certificateType = typeOfCert,
                provisionPlace = placeOfCert
            )
        )
        Log.e("~~~", resp.toString())
        sendCertificateUseCase(resp).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _sendState.value = SendCertificateState(
                        success = result.data != null
                    )
                }

                is Resource.Loading -> {
                    _sendState.value = SendCertificateState(isLoading = true)
                }

                is Resource.Error -> {
                    _sendState.value = SendCertificateState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}