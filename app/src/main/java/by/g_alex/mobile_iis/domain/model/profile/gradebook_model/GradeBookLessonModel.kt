package by.g_alex.mobile_iis.domain.model.profile.gradebook_model

import by.g_alex.mobile_iis.data.local.entity.GradeBookEntity

data class GradeBookLessonModel(
    val controlPoint: String,
    val dateString: String,
    val gradeBookOmissions: Int,
    val lessonNameAbbrev: String,
    val lessonTypeAbbrev: String,
    val lessonTypeId: Int,
    val marks: List<Int>
)
fun GradeBookLessonModel.toGradeBookEntity():GradeBookEntity{
    return GradeBookEntity(
        controlPoint = controlPoint,
        dateString = dateString,
        gradeBookOmissions = gradeBookOmissions,
        lessonTypeId = lessonTypeId,
        lessonTypeAbbrev = lessonTypeAbbrev,
        lessonNameAbbrev = lessonNameAbbrev,
        marks = marks
    )
}
