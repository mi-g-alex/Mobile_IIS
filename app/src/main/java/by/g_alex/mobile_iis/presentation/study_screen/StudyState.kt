package by.g_alex.mobile_iis.presentation.study_screen

import by.g_alex.mobile_iis.data.remote.dto.study.StudyDto

data class StudyState (
    val isLoading: Boolean = false,
    val studyAll: StudyDto? = null,
    val error: String = ""
)