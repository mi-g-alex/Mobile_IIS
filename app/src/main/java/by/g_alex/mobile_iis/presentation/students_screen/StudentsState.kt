package by.g_alex.mobile_iis.presentation.students_screen

import by.g_alex.mobile_iis.data.remote.dto.students.StudentResponseDto

data class StudentsState(
    val isLoading: Boolean = false,
    val StudentsState: StudentResponseDto? = null,
    val error: String = ""
)
