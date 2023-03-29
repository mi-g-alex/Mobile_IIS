package com.example.compose.presentation.states

import com.example.compose.domain.model.LessonModel
import com.example.compose.domain.model.PrepodModel

data class PrepodsState(
    val isLoading: Boolean = false,
    var preps : List<PrepodModel>? = emptyList(),
    val error:String = ""
)
