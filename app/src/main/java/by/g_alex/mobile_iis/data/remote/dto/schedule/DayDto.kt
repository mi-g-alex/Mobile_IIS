package by.g_alex.mobile_iis.data.remote.dto.schedule

import android.util.Log
import by.g_alex.mobile_iis.data.local.entity.LessonModel

data class DayDto(
    val auditories: List<String>,
    val endLessonTime: String,
    val lessonTypeAbbrev: String,
    val note: String?,
    val numSubgroup: Int,
    val startLessonTime: String,
    val studentGroups: List<StudentGroupsDto>,
    val subject: String,
    val subjectFullName: String,
    val weekNumber: List<Int>,
    val employees: List<EmployeeDto>?,
    val dateLesson: Any?,
    val startLessonDate: String,
    val endLessonDate: String,
    val announcementStart: Any,
    val announcementEnd: Any,
    val announcement: Boolean,
    val split: Boolean
)

fun DayDto.toLessonModel(weekDay: String): LessonModel {
    Log.w("||||||||||", this.toString())
    val str = if (employees?.isNotEmpty() == true)
        employees[0].lastName + " " + employees[0].firstName[0] + "." + employees[0].middleName[0] + "."
    else
        ""
    return LessonModel(
        id = studentGroups[0].name,
        auditories = auditories,
        endLessonTime = endLessonTime,
        lessonTypeAbbrev = lessonTypeAbbrev,
        numSubgroup = numSubgroup,
        startLessonTime = startLessonTime,
        subject = subject,
        subjectFullName = subjectFullName,
        weekNumber = weekNumber,
        fio = str,
        note = note,
        weekDay = weekDay
    )
}