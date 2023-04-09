package by.g_alex.mobile_iis.presentation.study_screen

import by.g_alex.mobile_iis.data.remote.dto.study.StudyApplicationsDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyCertificationsDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyMarkSheetDto

data class StudyState (
    val isLoading: Boolean = false,
    val studyMarkSheet: List<StudyMarkSheetDto>? = null,
    val studyCertificate: List<StudyCertificationsDto>? = null,
    val studyApplications: List<StudyApplicationsDto>? = null,
    val studyLibDebts: List<String>? = null,
    val error: String = ""
)