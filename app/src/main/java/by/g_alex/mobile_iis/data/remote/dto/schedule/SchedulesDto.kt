package by.g_alex.mobile_iis.data.remote.dto.schedule

import com.google.gson.annotations.SerializedName

data class SchedulesDto(
    @SerializedName("Суббота")
    val Saturday: List<DayDto>?,

    @SerializedName("Четверг")
    val Thursday: List<DayDto>?,

    @SerializedName("Пятница")
    val Friday: List<DayDto>?,

    @SerializedName("Вторник")
    val Tuesday: List<DayDto>?,

    @SerializedName("Понедельник")
    val Monday: List<DayDto>?,

    @SerializedName("Среда")
    val Wednesday: List<DayDto>?,
)