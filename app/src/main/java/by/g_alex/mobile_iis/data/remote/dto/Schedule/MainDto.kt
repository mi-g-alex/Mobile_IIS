package com.example.compose.data.remote.dto.Schedule

import by.g_alex.mobile_iis.data.local.entity.LessonModel

data class MainDto(
    val employeeDto: Employee?,
    val endDate: String,
    val endExamsDate: String?,
    val exams: List<Any>,
    val schedules: Schedules,
    val startDate: String,
    val startExamsDate: String?,
    val studentGroupDto: StudentGroupDto
)
fun MainDto.toLessonList() : List<LessonModel> {
    val mo = mutableListOf<LessonModel>()
    with(schedules){
        Monday?.onEach { mnd -> mo.add(mnd.toLessonModel("MONDAY")) }
        Tuesday?.onEach { mnd -> mo.add(mnd.toLessonModel("TUESDAY")) }
        Wednesday?.onEach { mnd -> mo.add(mnd.toLessonModel("WEDNESDAY")) }
        Thursday?.onEach { mnd -> mo.add(mnd.toLessonModel("THURSDAY")) }
        DayDto?.onEach { mnd->mo.add(mnd.toLessonModel("FRIDAY")) }
        Saturday?.onEach { mnd -> mo.add(mnd.toLessonModel("SATURDAY")) }
    }
    return mo.toList()
}