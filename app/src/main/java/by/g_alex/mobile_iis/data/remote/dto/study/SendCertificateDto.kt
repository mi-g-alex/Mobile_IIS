package by.g_alex.mobile_iis.data.remote.dto.study

data class SendCertificateDto(
    val certificateCount: Int?, // 1
    val certificateRequestDto: CertificateRequestDto?
) {
    data class CertificateRequestDto(
        val certificateType: String?, // обычная
        val provisionPlace: String? // по месту работы родителей
    )
}