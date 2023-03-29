package com.example.compose.data.remote.dto.prepodSchedule

import com.google.gson.annotations.SerializedName

data class Schedules(
    @SerializedName("Вторник") val Tuesday: List<PrepDayDto>?,
    @SerializedName("Понедельник")val Monday: List<PrepDayDto>?,
    @SerializedName("Пятница")val DayDto: List<PrepDayDto>?,
    @SerializedName("Среда")val Wednesday: List<PrepDayDto>?,
    @SerializedName("Суббота")val Saturday: List<PrepDayDto>?,
    @SerializedName("Четверг")val Thursday: List<PrepDayDto>?
)