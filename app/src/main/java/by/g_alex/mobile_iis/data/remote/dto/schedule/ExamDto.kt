package by.g_alex.mobile_iis.data.remote.dto.schedule

import by.g_alex.mobile_iis.data.local.entity.LessonModel

data class ExamDto(
    val announcement: Boolean?,
    val auditories: List<String>?,
    val dateLesson: String?,
    val employees: List<Employee>?,
    val endLessonDate: String?,
    val endLessonTime: String?,
    val lessonTypeAbbrev: String?,
    val note: String?,
    val numSubgroup: Int?,
    val split: Boolean?,
    val startLessonDate: String?,
    val startLessonTime: String?,
    val studentGroups: List<StudentGroup>?,
    val subject: String?,
    val subjectFullName: String?,
    val weekNumber: List<Int>?
)
fun ExamDto.toLessonModel(): LessonModel {
    return LessonModel(
        id = studentGroups?.get(0)?.name ?:"",
        auditories = auditories,
        endLessonTime = endLessonTime,
        lessonTypeAbbrev = lessonTypeAbbrev,
        numSubgroup = numSubgroup?:0,
        startLessonTime = startLessonTime,
        subject = subject,
        subjectFullName = subjectFullName,
        weekNumber = weekNumber,
        fio = "",
        note = note,
        weekDay = "22",
        type = false,
        groupNum = "",//.toString().substring(1,studentGroups.toString().length-2)}
        dateEnd = dateLesson
    )
}