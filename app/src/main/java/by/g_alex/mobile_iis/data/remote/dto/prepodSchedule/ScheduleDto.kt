package by.g_alex.mobile_iis.data.remote.dto.prepodSchedule

import by.g_alex.mobile_iis.data.local.entity.LessonModel

data class ScheduleDto(
    val employeeDto: EmployeeDto,
    val endDate: String,
    val endExamsDate: Any,
    val exams: List<Any>?,
    val schedulesDto: SchedulesDto,
    val startDate: String,
    val startExamsDate: String?,
    val studentGroupDto: StudentGroupDto?
)

fun ScheduleDto.toLessonList(): List<LessonModel> {
    val mo = mutableListOf<LessonModel>()
    with(schedulesDto) {
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