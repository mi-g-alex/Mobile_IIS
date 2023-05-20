package by.g_alex.mobile_iis.presentation.schedule.states

import by.g_alex.mobile_iis.data.local.entity.LessonModel

data class ExamState(
    val isLoading: Boolean = false,
    var exams : List<LessonModel>? = emptyList(),
    val error:String = ""
)
