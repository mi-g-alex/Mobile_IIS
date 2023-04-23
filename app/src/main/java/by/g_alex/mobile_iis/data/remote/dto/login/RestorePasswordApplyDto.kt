package by.g_alex.mobile_iis.data.remote.dto.login

data class RestorePasswordApplyDto(
    val login : String,
    val contactValue: String,
    val confirmCode: String,
    val password: String
)
