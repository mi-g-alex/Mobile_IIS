package com.example.compose.data.remote.dto.prepod

import com.example.compose.domain.model.PrepodModel

data class PrepodDto(
    val academicDepartment: List<String>,
    val calendarId: String,
    val degree: String,
    val fio: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val middleName: String,
    val photoLink: String,
    val rank: String,
    val urlId: String
)
fun PrepodDto.toPrepodModel():PrepodModel{
    return PrepodModel(
        academicDepartment = academicDepartment,
        degree = degree,
        fio = fio,
        firstName = firstName,
        id = id,
        lastName = lastName,
        middleName = middleName,
        photoLink = photoLink,
        rank = rank,
        urlId = urlId
    )
}