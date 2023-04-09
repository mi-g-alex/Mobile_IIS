package by.g_alex.mobile_iis.presentation.study_screen

import by.g_alex.mobile_iis.data.remote.dto.use_group.UserGroupDto

data class StudyState (
    val isLoading: Boolean = false,
    val userGroupState: UserGroupDto? = null,
    val error: String = ""
)