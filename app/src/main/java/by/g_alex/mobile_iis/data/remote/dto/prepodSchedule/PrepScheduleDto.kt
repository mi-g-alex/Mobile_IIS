package com.example.compose.data.remote.dto.prepodSchedule

import com.example.compose.domain.model.LessonModel

data class PrepScheduleDto(
    val employeeDto: EmployeeDto,
    val endDate: String,
    val endExamsDate: Any,
    val exams: List<Any>?,
    val schedules: Schedules,
    val startDate: String,
    val startExamsDate: String?,
    val studentGroupDto: StudentGroup?
)

fun PrepScheduleDto.toLessonList(): List<LessonModel> {
    val mo = mutableListOf<LessonModel>()
    with(schedules) {
        Monday?.onEach { mnd ->
            val trans = mnd.toLessonModel("MONDAY")
            trans.id = employeeDto.urlId
            mo.add(trans)
        }
        Tuesday?.onEach { mnd ->
            val trans = mnd.toLessonModel("TUESDAY")
            trans.id = employeeDto.urlId
            mo.add(trans)
        }
        Wednesday?.onEach { mnd ->
            val trans = mnd.toLessonModel("WEDNESDAY")
            trans.id = employeeDto.urlId
            mo.add(trans)
        }
        Thursday?.onEach { mnd ->
            val trans = mnd.toLessonModel("THURSDAY")
            trans.id = employeeDto.urlId
            mo.add(trans)
        }
        DayDto?.onEach { mnd ->
            val trans = mnd.toLessonModel("FRIDAY")
            trans.id = employeeDto.urlId
            mo.add(trans)
        }
        Saturday?.onEach { mnd ->
            val trans = mnd.toLessonModel("SATURDAY")
            trans.id = employeeDto.urlId
            mo.add(trans)
        }
    }
    return mo.toList()
}