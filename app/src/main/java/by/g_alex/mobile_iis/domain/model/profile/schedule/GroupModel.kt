package com.example.compose.domain.model

data class GroupModel(
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