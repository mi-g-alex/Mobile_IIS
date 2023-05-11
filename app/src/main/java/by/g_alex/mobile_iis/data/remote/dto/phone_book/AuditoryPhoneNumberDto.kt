package by.g_alex.mobile_iis.data.remote.dto.phone_book

data class AuditoryPhoneNumberDto(
    val auditory: String,
    val buildingAddress: String,
    val departments: List<Department>,
    val employees: List<Employee>,
    val note: String,
    val phones: List<String>
)