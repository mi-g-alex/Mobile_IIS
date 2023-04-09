package by.g_alex.mobile_iis.data.remote.dto.study

data class StudyMarkSheetDto(
    val absentDate: String?, // string
    val certificate: Boolean?, // true
    val createDate: String?, // string
    val employee: Employee?,
    val expireDate: String?, // string
    val hours: Int?, // 0
    val id: Int?, // 0
    val markSheetType: MarkSheetType?,
    val number: String?, // string
    val paymentFormMap: PaymentFormMap?,
    val price: Int?, // 0
    val reason: Int?, // 0
    val rejectionReason: String?, // string
    val requestValidationDoc: String?, // string
    val retakeCount: Int?, // 0
    val status: String?, // string
    val subject: Subject?,
    val term: Int? // 0
) {
    data class Employee(
        val academicDepartment: String?, // string
        val fio: String?, // string
        val firstName: String?, // string
        val id: Int?, // 0
        val lastName: String?, // string
        val middleName: String?, // string
        val price: Int? // 0
    )

    data class MarkSheetType(
        val coefficient: Int?, // 0
        val fullName: String?, // string
        val id: Int?, // 0
        val isCourseWork: Boolean?, // true
        val isExam: Boolean?, // true
        val isLab: Boolean?, // true
        val isOffset: Boolean?, // true
        val isRemote: Boolean?, // true
        val price: Int?, // 0
        val shortName: String? // string
    )

    data class PaymentFormMap(
        val additionalProp1: String?, // string
        val additionalProp2: String?, // string
        val additionalProp3: String? // string
    )

    data class Subject(
        val abbrev: String?, // string
        val focsId: Int?, // 0
        val id: Int?, // 0
        val lessonTypeAbbrev: String?, // string
        val name: String?, // string
        val term: Int?, // 0
        val thId: Int? // 0
    )
}
