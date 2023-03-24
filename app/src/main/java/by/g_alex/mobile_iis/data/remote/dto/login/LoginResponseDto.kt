package by.g_alex.mobile_iis.data.remote.dto.login

data class LoginResponseDto(
    val authorities: List<String> = emptyList(),
    val canStudentNote: Boolean = false, // false
    val email: String = "", // sashagorgun@yandex.com
    val fio: String = "", // Горгун Александр Витальевич
    val group: String = "", // 253501
    val hasNotConfirmedContact: Boolean = false, // false
    val isGroupHead: Boolean = false, // false
    val phone: String = "", // +375291679827
    val photoUrl: String? = "", // https://drive.google.com/uc?id=1yRyNQ0sEVx3gnnztawY1KBV_uxmKm2B3&export=download
    val username: String = "" // 25350033
)