package by.g_alex.mobile_iis.data.remote.dto.schedule

import by.g_alex.mobile_iis.data.local.entity.LessonModel

data class MainDto(
    val startDate: String?,
    val endDate: String?,
    val startExamsDate: String?,
    val endExamsDate: String?,
    val employeeDto: EmployeeDto?,
    val studentGroupDto: StudentGroupDto?,
    val schedules: SchedulesDto?,
    val exams: List<Any>
) {
    fun toLessonList(): List<LessonModel> {
        val mo = mutableListOf<LessonModel>()
        with(schedules) {
            this?.Monday?.onEach { mnd -> mo.add(mnd.toLessonModel("MONDAY")) }
            this?.Tuesday?.onEach { mnd -> mo.add(mnd.toLessonModel("TUESDAY")) }
            this?.Wednesday?.onEach { mnd -> mo.add(mnd.toLessonModel("WEDNESDAY")) }
            this?.Thursday?.onEach { mnd -> mo.add(mnd.toLessonModel("THURSDAY")) }
            this?.Friday?.onEach { mnd -> mo.add(mnd.toLessonModel("FRIDAY")) }
            this?.Saturday?.onEach { mnd -> mo.add(mnd.toLessonModel("SATURDAY")) }
        }
        return mo.toList()
    }
}