package by.g_alex.mobile_iis.data.remote.dto.phone_book

data class PhoneSearchDto(
    val auditoryPhoneNumberDtoList: List<AuditoryPhoneNumberDto>,
    val totalItems: Int
)