package by.g_alex.mobile_iis.presentation.mark_book

import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel

data class MarkBookState (
    val isLoading: Boolean = false,
    val markBookState: List<MarkBookMarkModel>? = emptyList(),
    val error: String = ""
)