package by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet

data class MarkSheetSubjectsDto(
    val abbrev: String?, // БелЯз(ПЛ)
    val etId: Int?, // 196812
    val lessonTypes: List<LessonType?>?,
    val term: Int? // 2
) {
    data class LessonType(
        val abbrev: String?, // Зач.
        val focsId: Int?, // 204591
        val isCourseWork: Boolean?, // false
        val isExam: Boolean?, // false
        val isLab: Boolean?, // false
        val isOffset: Boolean?, // true
        val isRemote: Boolean?, // false
        val thId: Int? // 574343
    )
}
