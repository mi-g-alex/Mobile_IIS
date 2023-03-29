package com.example.compose.data.remote.dto.prepodSchedule

import androidx.compose.runtime.mutableStateOf
import com.example.compose.domain.model.LessonModel

data class PrepDayDto(
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
    val studentGroups: List<StudentGroup>,
    val subject: String,
    val subjectFullName: String,
    val weekNumber: List<Int>
)

fun PrepDayDto.toLessonModel(weekDay:String): LessonModel {
    val notation = mutableStateOf("")
    for(n in studentGroups.indices){
        notation.value+=studentGroups[n].name
        if(n!=studentGroups.size-1)
            notation.value+=", "
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