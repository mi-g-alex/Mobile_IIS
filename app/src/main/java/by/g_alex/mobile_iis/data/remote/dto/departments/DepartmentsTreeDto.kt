package by.g_alex.mobile_iis.data.remote.dto.departments

data class DepartmentsTreeDto(
    val children: List<DepartmentsTreeDto>?,
    val data: Data?
) {

    data class Data(
        val abbrev: String?, // ЦКР
        val code: String?, // 8
        val id: Int?, // 117
        val idHead: Any?, // null
        val name: String?, // Центр кадровой работы
        val numberOfEmployee: Int?, // 1
        val oldCode: String?, // 8
        val typeId: Int? // 1
    )
}
