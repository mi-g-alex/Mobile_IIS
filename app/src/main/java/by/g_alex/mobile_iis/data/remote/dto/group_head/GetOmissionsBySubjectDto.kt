package by.g_alex.mobile_iis.data.remote.dto.group_head

data class GetOmissionsBySubjectDto(
    val student: Student?,
    val students: List<Student?>?
) {
    data class Student(
        val fio: String?, // Анкуда Олег Дмитриевич
        val id: Int?, // 543533
        val lessons: List<Lesson?>?,
        val subGroup: Int?, // 1
        val subGroupStudent: Int? // 2
    ) {
        data class Lesson(
            val controlPoint: Any?, // null
            val dateString: String?, // 28.02.2023
            val gradeBookOmissions: Int?, // 4
            val id: Int?, // 769390
            val lessonNameAbbrev: String?, // ОАиП
            val lessonTypeAbbrev: String?, // ЛР
            val lessonTypeId: Int?, // 4
            val marks: List<Int?>?,
            val subGroup: Int? // 1
        )
    }
}
