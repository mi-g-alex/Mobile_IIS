package by.g_alex.mobile_iis.data.remote.dto.study

data class StudyApplicationsDto(
    val applicationDate: String?, // string
    val decisionDate: String?, // string
    val lmsApplicationId: Int?, // 0
    val paymentFormAvailable: Boolean?, // true
    val purposeOfStudy: String?, // string
    val rejectionReason: String?, // string
    val status: String?, // string
    val statusId: Int?, // 0
    val subject: String?, // string
    val term: Int? // 0
)