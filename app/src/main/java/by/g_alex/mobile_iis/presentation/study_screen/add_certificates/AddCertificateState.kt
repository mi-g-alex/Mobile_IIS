package by.g_alex.mobile_iis.presentation.study_screen.add_certificates

import by.g_alex.mobile_iis.data.remote.dto.study.CertificatePlacesDto

data class AddCertificateState (
    val isLoading: Boolean = false,
    val listOfItems: List<CertificatePlacesDto>? = null,
    val error: String = ""
)