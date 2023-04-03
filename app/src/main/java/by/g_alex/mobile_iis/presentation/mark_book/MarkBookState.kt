package by.g_alex.mobile_iis.presentation.mark_book

import by.g_alex.mobile_iis.data.remote.dto.mark_book.MarkBookDto

data class MarkBookState (
    val isLoading: Boolean = false,
    val markBookState: MarkBookDto? = null,
    val error: String = ""
)