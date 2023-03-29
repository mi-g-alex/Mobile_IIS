package by.g_alex.mobile_iis.data.remote.dto.prepodSchedule

import androidx.compose.runtime.mutableStateOf
import by.g_alex.mobile_iis.data.local.entity.LessonModel

data class ScheduleDayDto(
    val announcement: Boolean,
    val announcementEnd: Any,
    val announcementStart: Any,
    val auditories: List<String>,
    val dateLesson: String?,
    //val employees: List<EmployeeDto>,
    val endLessonDate: String,
    val endLessonTime: String,
    val lessonTypeAbbrev: String,
    val note: String?,
    val numSubgroup: Int,
    val split: Boolean,
    val startLessonDate: String,
    val startLessonTime: String,
    val studentGroupDtos: List<StudentGroupDto>,
    val subject: String,
    val subjectFullName: String,
    val weekNumber: List<Int>
)

fun ScheduleDayDto.toLessonModel(weekDay: String): LessonModel {
    val notation = mutableStateOf("")
    for (n in studentGroupDtos.indices) {
        notation.value += studentGroupDtos[n].name
        if (n != studentGroupDtos.size - 1)
            notation.value += ", "
    }
    return LessonModel(
        id = "",
        auditories = auditories,
        endLessonTime = endLessonTime,
        lessonTypeAbbrev = lessonTypeAbbrev,
        numSubgroup = numSubgroup,
        startLessonTime = startLessonTime,
        subject = subject,
        subjectFullName = subjectFullName,
        weekNumber = weekNumber,
        fio = notation.value,
        note = note,
        weekDay = weekDay
    )
}