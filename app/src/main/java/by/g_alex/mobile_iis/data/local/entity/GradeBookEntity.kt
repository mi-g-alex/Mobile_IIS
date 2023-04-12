package by.g_alex.mobile_iis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel

@Entity
data class GradeBookEntity(
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    val controlPoint: String,
    val dateString: String,
    val gradeBookOmissions: Int,
    val lessonNameAbbrev: String,
    val lessonTypeAbbrev: String,
    val lessonTypeId: Int,
    val marks: List<Int>
)
fun GradeBookEntity.toGradeBookLessonModel():GradeBookLessonModel{
    return GradeBookLessonModel(
        controlPoint = controlPoint,
        dateString = dateString,
        gradeBookOmissions = gradeBookOmissions,
        lessonNameAbbrev = lessonNameAbbrev,
        lessonTypeAbbrev = lessonTypeAbbrev,
        lessonTypeId = lessonTypeId,
        marks = marks
    )
}