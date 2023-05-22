package by.g_alex.mobile_iis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PrivilegesDto(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    val dormitoryPrivilegeCategoryId: Int,
    val dormitoryPrivilegeCategoryName: String,
    val dormitoryPrivilegeId: Int,
    val dormitoryPrivilegeName: String,
    val id: Int,
    val note: String,
    val studentId: Int,
    val year: Int
)