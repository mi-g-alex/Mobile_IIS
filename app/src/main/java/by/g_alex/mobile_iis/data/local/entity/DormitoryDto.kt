package by.g_alex.mobile_iis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DormitoryDto(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    val acceptedDate: Long?,
    val applicationDate: Long?,
    val docContent: String?,
    val docReference: String?,
    val id: Int?,
    val number: Int?,
    val numberInQueue: Int?,
    val rejectionReason: String?,
    val roomInfo: String?,
    val settledDate: Long?,
    val status: String?
)