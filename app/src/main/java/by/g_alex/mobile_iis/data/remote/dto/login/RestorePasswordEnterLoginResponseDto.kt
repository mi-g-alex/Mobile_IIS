package by.g_alex.mobile_iis.data.remote.dto.login

import kotlinx.serialization.Serializable

@Serializable
data class RestorePasswordEnterLoginResponseDto(
    val contacts: List<Contact?>?,
    val employee: Boolean?, // false
    val remainingAttempts: Int // 4
) {
    @Serializable
    data class Contact(
        val codeExpirationTime: String?, // null
        val contactTypeId: Int?, // 6
        val contactValue: String? // sa*******@yandex.com
    )
}