package com.example.compose.presentation.states

import com.example.compose.domain.model.GroupModel

data class GroupState(
    val isLoading: Boolean = false,
    var Groups : List<GroupModel> = emptyList(),
    val error:String = ""
)