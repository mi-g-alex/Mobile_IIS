package by.g_alex.mobile_iis.data.remote.dto.departments

data class DepartmentEmployeesDto(
    val calendarId: String?, // k2ecr5nj6j3m45f3pk31ji7l1s@group.calendar.google.com
    val degree: String?,
    val degreeAbbrev: String?,
    val department: Any?, // null
    val email: String?, // v.vladymtsev@bsuir.by
    val firstName: String?, // Вадим
    val id: Int?, // 536343
    val jobPositions: List<JobPosition?>?,
    val lastName: String?, // Владымцев
    val middleName: String?, // Денисович
    val photoLink: String?, // https://iis.bsuir.by/api/v1/employees/photo/536343
    val rank: String?, // доцент
    val urlId: String? // v-vladymtsev
) {
    data class JobPosition(
        val contacts: List<Contact?>?,
        val department: String?, // Каф.информатики
        val jobPosition: String? // ассистент
    ) {
        data class Contact(
            val address: String?, // каб. 112-4 к., ул. Гикало, 9
            val phoneNumber: String? // +375172938987
        )
    }
}