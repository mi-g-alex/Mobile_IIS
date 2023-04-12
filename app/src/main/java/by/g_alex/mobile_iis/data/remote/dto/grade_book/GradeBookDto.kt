package by.g_alex.mobile_iis.data.remote.dto.grade_book

import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel

data class GradeBookDto(
    val student: Student,
    val students: List<Student>
)

fun GradeBookDto.toGradeBookLessonModel(): List<GradeBookLessonModel> {
    val gradeList = mutableListOf<GradeBookLessonModel>()
    student.lessons.onEach {
        gradeList.add(GradeBookLessonModel(
            controlPoint = it.controlPoint,
            dateString = it.dateString,
            gradeBookOmissions = it.gradeBookOmissions,
            lessonTypeId = it.lessonTypeId,
            lessonNameAbbrev = it.lessonNameAbbrev,
            lessonTypeAbbrev = it.lessonTypeAbbrev,
            marks = it.marks
        ))
    }
    return gradeList
}