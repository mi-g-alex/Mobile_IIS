package com.example.compose.data.remote.dto.group

import com.example.compose.domain.model.GroupModel

data class GroupDtoItem(
    val calendarId: String,
    val course: Int,
    val educationDegree: Int,
    val facultyAbbrev: String,
    val facultyId: Int,
    val id: Int,
    val name: String,
    val specialityAbbrev: String,
    val specialityDepartmentEducationFormId: Int,
    val specialityName: String
)

fun GroupDtoItem.toGroupModel(): GroupModel {
    return GroupModel(
        calendarId = calendarId,
        course = course,
        educationDegree = educationDegree,
        facultyAbbrev = facultyAbbrev,
        facultyId = facultyId,
        id = id,
        name = name,
        specialityAbbrev = specialityAbbrev,
        specialityDepartmentEducationFormId = specialityDepartmentEducationFormId,
        specialityName = specialityName
    )
}