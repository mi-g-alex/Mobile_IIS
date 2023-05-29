package by.g_alex.mobile_iis.data.remote.dto.group_head

data class GetOmissionsByDateDto(
    val lessons: List<Lesson>
) {
    data class Lesson(
        val dateString: String, // 25-05-2023
        val id: Int, // 758694
        val lessonPeriod: LessonPeriod,
        val lessonTypeAbbrev: String, // ПЗ
        val nameAbbrev: String, // КЧ
        val students: List<Student>?,
        val subGroup: Int // 0
    ) {
        data class LessonPeriod(
            val endTime: String, // 17:10
            val lessonPeriodHours: Int, // 2
            val startTime: String // 15:50
        )

        data class Student(
            val fio: String, // Анкуда Олег Дмитриевич
            val id: Int, // 543533
            val omission: Omission?
        ) {
            data class Omission(
                val id: Int, // 732562
                val missedHours: Int // 1
            )
        }
    }
}