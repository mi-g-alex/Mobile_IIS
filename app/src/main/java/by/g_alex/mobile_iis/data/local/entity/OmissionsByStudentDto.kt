package by.g_alex.mobile_iis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class OmissionsByStudentDto(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    val dateFrom: Long, // 1678482000000
    val dateTo: Long, // 1678482000000
    val id: Int, // 25817
    val name: String, // Заявление студента
    val note: String?, // CSC
    val term: String // 2
)
