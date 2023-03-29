package com.example.compose.domain.model

data class PrepodModel(
    val academicDepartment: List<String>,
    val degree: String,
    val fio: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val middleName: String,
    val photoLink: String,
    val rank: String?,
    val urlId: String
)
