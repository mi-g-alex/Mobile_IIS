package by.g_alex.mobile_iis.data.remote.dto.prepodSchedule

import com.google.gson.annotations.SerializedName

data class SchedulesDto(
    @SerializedName("Вторник") val Tuesday: List<ScheduleDayDto>?,
    @SerializedName("Понедельник") val Monday: List<ScheduleDayDto>?,
    @SerializedName("Пятница") val DayDto: List<ScheduleDayDto>?,
    @SerializedName("Среда") val Wednesday: List<ScheduleDayDto>?,
    @SerializedName("Суббота") val Saturday: List<ScheduleDayDto>?,
    @SerializedName("Четверг") val Thursday: List<ScheduleDayDto>?
)