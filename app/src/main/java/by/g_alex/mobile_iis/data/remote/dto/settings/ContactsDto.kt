package by.g_alex.mobile_iis.data.remote.dto.settings

data class ContactsDto(
    val contactDtoList: List<ContactDto?>?,
    val numberOfAttempts: Int? // 4
) {
    data class ContactDto(
        val codeExpirationTime: Any?, // null
        val confirmed: Boolean?, // true
        val contactTypeId: Int?, // 6
        val contactValue: String?, // sashagorgun@yandex.by
        val id: Int? // 823779
    )
}