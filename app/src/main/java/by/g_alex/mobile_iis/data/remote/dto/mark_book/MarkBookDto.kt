package by.g_alex.mobile_iis.data.remote.dto.mark_book

data class MarkBookDto(
    val averageMark: Double, // 9.17
    val markPages: Map<Int,MapValue>,
    val number: String // 25350033
) {
    data class MapValue(
        val averageMark: Double, // 9.17
        val marks: List<Mark>
    ) {
        data class Mark(
            val applicationHasAlreadyBeenSentForAcademicDifferences: Any?, // null
            val applicationHasAlreadyBeenSentForParallel: Any?, // null
            val canLiquidationAcademicDifferences: Any?, // null
            val canStudyInParallel: Any?, // null
            val commonMark: Double?, // 9.158940397350994
            val commonRetakes: Double?, // 0.013245033112582781
            val date: String, // 24.12.2022
            val formOfControl: String, // Диф.зачет
            val fullSubject: String, // Логика
            val hours: String, // 108.0
            val idFormOfControl: Int, // 217
            val idSubject: Int, // 20437
            val lmsEducationTerms: Any?, // null
            val mark: String, // 7
            val retakesCount: Int, // 0
            val subject: String, // Логика
            val teacher: String // Бархатков А. И.
        )
    }
}