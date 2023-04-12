package by.g_alex.mobile_iis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginAndPasswordEntity(
    @PrimaryKey val alwaysField: Int = 0,
    val login: String?,
    val password: String?
)