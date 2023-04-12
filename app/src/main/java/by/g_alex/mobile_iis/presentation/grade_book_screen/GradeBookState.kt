package by.g_alex.mobile_iis.presentation.grade_book_screen

import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel

data class GradeBookState (
    val isLoading: Boolean = false,
    val gradeBookState: List<GradeBookLessonModel>? = null,
    val error: String = ""
)