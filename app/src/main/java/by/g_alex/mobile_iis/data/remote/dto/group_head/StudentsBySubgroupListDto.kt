package by.g_alex.mobile_iis.data.remote.dto.group_head

data class StudentsBySubgroupListDto(
    val assignedByEmployee: Boolean?, // false
    val gradeBookSubGroupStudentDtoList: List<GradeBookSubGroupStudentDto?>?
) {
    data class GradeBookSubGroupStudentDto(
        val fio: String?, // Анкуда Олег Дмитриевич
        val studentId: Int?, // 543533
        val subGroup: Any? // null
    )
}