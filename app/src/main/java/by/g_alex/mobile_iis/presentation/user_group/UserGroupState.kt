package by.g_alex.mobile_iis.presentation.user_group

import by.g_alex.mobile_iis.data.remote.dto.mark_book.MarkBookDto
import by.g_alex.mobile_iis.data.remote.dto.use_group.UserGroupDto

data class UserGroupState (
    val isLoading: Boolean = false,
    val userGroupState: UserGroupDto? = null,
    val error: String = ""
)