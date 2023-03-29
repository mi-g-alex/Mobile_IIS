package com.example.compose.presentation.states

import com.example.compose.domain.model.LessonModel

data class ScheduleState(
    val isLoading: Boolean = false,
    var Days : List<LessonModel>? = emptyList(),
    val error:String = ""
)
