package by.g_alex.mobile_iis.data.remote.dto.user_group

data class UserGroupDto(
    val groupInfoStudentDto: List<GroupInfoStudentDto>,
    val numberOfGroup: String, // 253501
    val studentGroupCuratorDto: StudentGroupCuratorDto?
) {
    data class GroupInfoStudentDto(
        val fio: String, // Азаров Егор Антонович
        val position: String,
        val urlId: String
    )

    data class StudentGroupCuratorDto(
        val email: String?, // stroinikova@bsuir.by
        val fio: String, // Стройникова Елена Дмитриевна
        val phone: String?, // +375172938666
        val position: String, // Куратор
        val urlId: String // e-strojnikova
    )
}