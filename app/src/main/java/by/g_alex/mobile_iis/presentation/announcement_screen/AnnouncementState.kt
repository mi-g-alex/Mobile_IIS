package by.g_alex.mobile_iis.presentation.announcement_screen

import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto

data class AnnouncementState (
    val isLoading: Boolean = false,
    val anonsState: List<AnnouncemntDto>? = null,
    val error: String = ""
)