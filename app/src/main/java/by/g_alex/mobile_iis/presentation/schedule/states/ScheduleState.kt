package by.g_alex.mobile_iis.presentation.schedule.states

import by.g_alex.mobile_iis.data.local.entity.LessonModel

data class ScheduleState(
    val isLoading: Boolean = false,
    var Days : List<LessonModel>? = emptyList(),
    val error:String = ""
)
