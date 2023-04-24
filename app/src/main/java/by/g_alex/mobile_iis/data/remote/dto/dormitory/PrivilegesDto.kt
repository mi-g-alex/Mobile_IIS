package by.g_alex.mobile_iis.data.remote.dto.dormitory

data class PrivilegesDto(
    val dormitoryPrivilegeCategoryId: Int,
    val dormitoryPrivilegeCategoryName: String,
    val dormitoryPrivilegeId: Int,
    val dormitoryPrivilegeName: String,
    val id: Int,
    val note: String,
    val studentId: Int,
    val year: Int
)