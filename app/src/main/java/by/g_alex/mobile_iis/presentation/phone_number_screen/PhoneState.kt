package by.g_alex.mobile_iis.presentation.phone_number_screen

import by.g_alex.mobile_iis.data.remote.dto.phone_book.PhoneSearchDto

data class PhoneState(
    val isLoading: Boolean = false,
    val PhoneState: PhoneSearchDto? = null,
    val error: String = ""
)
