package by.g_alex.mobile_iis.data.remote.dto.study

data class CertificatePlacesDto(
    val places: List<Place>,
    val type: String? // СППС
) {
    data class Place(
        val id: Int, // 8
        val name: String?, // в ФСЗН
        val type: Int // 1
    )
}