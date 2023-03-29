package com.example.compose.presentation.states

data class CurrentWeekState(
    val isLoading: Boolean = false,
    var week : Int? = 0,
    val error:String = ""
)
