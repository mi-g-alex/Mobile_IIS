package by.g_alex.mobile_iis.presentation.schedule.states

import by.g_alex.mobile_iis.domain.model.profile.schedule.GroupModel

data class GroupState(
    val isLoading: Boolean = false,
    var Groups : List<GroupModel> = emptyList(),
    val error:String = ""
)