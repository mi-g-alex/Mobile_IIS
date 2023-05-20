package by.g_alex.mobile_iis.data.remote.dto.schedule

import by.g_alex.mobile_iis.data.local.entity.LessonModel

data class ExamDto(
    val announcement: Boolean?,
    val auditories: List<String>?,
    val dateLesson: String?,
    val employees: List<EmployeeDto>?,
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
    val str = if (employees?.isNotEmpty() == true)
        employees[0].lastName + " " + employees[0].firstName[0] + "." + employees[0].middleName[0] + "."
    else
        ""
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
        fio = str,
        note = note,
        weekDay = "22",
        type = true,
        groupNum = "",
        dateEnd = dateLesson
    )
}