package by.g_alex.mobile_iis.data.remote.dto.phone_book

data class RequestDto(
    val currentPage: Int,
    val pageSize: Int,
    val searchValue: String
)