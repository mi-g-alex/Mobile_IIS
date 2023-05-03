package by.g_alex.mobile_iis.data.remote.dto.diploma

data class DiplomaDto(
    val accepted: Boolean?, // true
    val date: String?, // string
    val employee: Employee?,
    val id: Int?, // 0
    val rejectionReason: String?, // string
    val status: String?, // string
    val statusId: Int?, // 0
    val student: Student?,
    val topic: String? // string
) {
    data class Employee(
        val academicDepartment: String?, // string
        val fio: String?, // string
        val firstName: String?, // string
        val id: Int?, // 0
        val lastName: String?, // string
        val middleName: String? // string
    )

    data class Student(
        val academicDepartment: String?, // string
        val fio: String?, // string
        val firstName: String?, // string
        val id: Int?, // 0
        val lastName: String?, // string
        val middleName: String?, // string
        val studentGroup: String? // string
    )
}