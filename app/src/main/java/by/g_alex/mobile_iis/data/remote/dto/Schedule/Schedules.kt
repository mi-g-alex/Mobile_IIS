package com.example.compose.data.remote.dto.Schedule

import com.google.gson.annotations.SerializedName

data class Schedules(
    @SerializedName("Вторник") val Tuesday: List<DayDto>?,
    @SerializedName("Понедельник")val Monday: List<DayDto>?,
    @SerializedName("Пятница")val DayDto: List<DayDto>?,
    @SerializedName("Среда")val Wednesday: List<DayDto>?,
    @SerializedName("Суббота")val Saturday: List<DayDto>?,
    @SerializedName("Четверг")val Thursday: List<DayDto>?
)