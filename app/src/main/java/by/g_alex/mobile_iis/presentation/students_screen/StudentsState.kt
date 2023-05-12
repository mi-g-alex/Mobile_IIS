package by.g_alex.mobile_iis.presentation.students_screen

import by.g_alex.mobile_iis.data.remote.dto.students.StudentResponceDto

data class StudentsState(
    val isLoading: Boolean = false,
    val StudentsState: StudentResponceDto? = null,
    val error: String = ""
)
