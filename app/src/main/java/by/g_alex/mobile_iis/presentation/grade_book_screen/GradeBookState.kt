package by.g_alex.mobile_iis.presentation.grade_book_screen

import by.g_alex.mobile_iis.data.remote.dto.grade_book.GradeBookDto

data class GradeBookState (
    val isLoading: Boolean = false,
    val gradeBookState: List<GradeBookDto> = emptyList(),
    val error: String = ""
)