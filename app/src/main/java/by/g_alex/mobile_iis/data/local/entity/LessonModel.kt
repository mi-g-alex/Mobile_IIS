package by.g_alex.mobile_iis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LessonModel(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    var id: String,
    val auditories: List<String>?,
    val endLessonTime: String?,
    val lessonTypeAbbrev: String?,
    val numSubgroup: Int,
    val startLessonTime: String?,
    val subject: String?,
    val subjectFullName: String?,
    val weekNumber: List<Int>?,
    val fio:String?,
    val note : String?,
    val weekDay : String?
)