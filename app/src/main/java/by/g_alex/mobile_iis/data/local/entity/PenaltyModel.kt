package by.g_alex.mobile_iis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PenaltyModel(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    val penaltyDate : String,
    val penaltyType : String,
    val reason : String
)
