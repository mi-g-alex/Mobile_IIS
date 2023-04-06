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
    fun toLessonList(type : Boolean): List<LessonModel> {
        val mo = mutableListOf<LessonModel>()
        with(schedules) {
            this?.Monday?.onEach { mnd -> mo.add(mnd.toLessonModel("MONDAY",type)) }
            this?.Tuesday?.onEach { mnd -> mo.add(mnd.toLessonModel("TUESDAY",type)) }
            this?.Wednesday?.onEach { mnd -> mo.add(mnd.toLessonModel("WEDNESDAY",type)) }
            this?.Thursday?.onEach { mnd -> mo.add(mnd.toLessonModel("THURSDAY",type)) }
            this?.Friday?.onEach { mnd -> mo.add(mnd.toLessonModel("FRIDAY",type)) }
            this?.Saturday?.onEach { mnd -> mo.add(mnd.toLessonModel("SATURDAY",type)) }
        }
        return mo.toList()
    }
}