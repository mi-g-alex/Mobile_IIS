package by.g_alex.mobile_iis.data.remote.dto.departments

data class EmployeeDetailInfoDto(
    val additionalInformation: List<AdditionalInformation?>?,
    val calendarId: String?, // 2he8md8im6mlfj0lrvdli82g08@group.calendar.google.com
    val degree: String?,
    val degreeAbbrev: String?,
    val department: String?, // null
    val email: String?, // migalevich@bsuir.by
    val firstName: String?, // Сергей
    val id: Int?, // 500043
    val jobPositions: List<JobPosition?>?,
    val lastName: String?, // Мигалевич
    val middleName: String?, // Александрович
    val photoLink: String?, // https://iis.bsuir.by/api/v1/employees/photo/500043
    val profileLinks: List<ProfileLink?>,
    val rank: Any?, // null
    val readingCourses: List<String?>?,
    val urlId: String? // s-migalevich
) {
    data class AdditionalInformation(
        val content: String?, // <p>Информационные технологии;</p><p>Управление персоналом.</p>
        val nameType: String? // Область профессиональных интересов/исследований
    )

    data class JobPosition(
        val contacts: List<Contact?>?,
        val department: String?, // ЦИИР
        val jobPosition: String? // начальник центра информатизации и инновационных разработок
    ) {
        data class Contact(
            val address: String?, // каб. 302-5 к., ул. Платонова, 39
            val phoneNumber: String? // +375172932320
        )
    }

    data class ProfileLink(
        val link: String?, // https://scholar.google.com/citations?hl=ru&user=mK7q1l8AAAAJ&view_op=list_works&gmla=AJsN-F5SxSTni_WV-xN8cLlP5mDZkA-4GtYyiNJHFtz-5DJ1zsXyqnc40o8XVYQGGFlseuO9sArvBr1B15WegRlXEyx41-snZn0UOB8PcpkWm4ekNxgKsGk
        val profileLinkEmployeeId: Int?, // 7
        val profileLinkType: String? // Google Scholar
    )
}