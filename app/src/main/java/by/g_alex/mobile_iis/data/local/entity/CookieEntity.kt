package by.g_alex.mobile_iis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CookieEntity(
    @PrimaryKey val alwaysField: Int = 0,
    val cookie: String?
)
