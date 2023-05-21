package by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet

data class MarkSheetTypesDto(
    val coefficient: Double?, // 0.4
    val fullName: String, // 1231231
    val id: Int, // 1
    val isCourseWork: Boolean?, // false
    val isExam: Boolean?, // true
    val isLab: Boolean?, // false
    val isOffset: Boolean?, // false
    val isRemote: Boolean?, // false
    val price: Int?, // null
    val shortName: String // Экз., диф. зачет (дн., веч., заоч.)
)