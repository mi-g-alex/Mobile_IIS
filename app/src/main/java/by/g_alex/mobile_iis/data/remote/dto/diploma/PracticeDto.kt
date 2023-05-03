package by.g_alex.mobile_iis.data.remote.dto.diploma

data class PracticeDto(
    val createDate: String?, // string
    val docReference: String?, // string
    val documentContent: String?, // string
    val employee: Employee?,
    val enterprise: Enterprise?,
    val id: Int?, // 0
    val number: String?, // string
    val practiceType: PracticeType?,
    val rejectionReason: String?, // string
    val status: String?, // string
    val statusId: Int? // 0
) {
    data class Employee(
        val academicDepartment: String?, // string
        val fio: String?, // string
        val firstName: String?, // string
        val id: Int?, // 0
        val lastName: String?, // string
        val middleName: String? // string
    )

    data class Enterprise(
        val id: Int?, // 0
        val name: String? // string
    )

    data class PracticeType(
        val id: Int?, // 0
        val name: String? // string
    )
}