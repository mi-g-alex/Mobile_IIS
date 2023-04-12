package by.g_alex.mobile_iis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel
@Entity
data class MarkBookEntity (
    @PrimaryKey(autoGenerate = true)
    val key: Int = 0,
    val pagesSize : Int,
    val averageMarkALL: Double, // 9.17
    val markPage: Int,
    val number: String ,// 25350033
    val averageMark: Double,
    val commonMark: Double?, // 9.158940397350994
    val commonRetakes: Double?, // 0.013245033112582781
    val date: String?, // 24.12.2022
    val formOfControl: String, // Диф.зачет
    val fullSubject: String, // Логика
    val hours: String, // 108.0
    val idFormOfControl: Int, // 217
    val idSubject: Int, // 20437
    val mark: String, // 7
    val retakesCount: Int, // 0
    val subject: String, // Логика
    val teacher: String? // Бархатков А. И.
    )
fun MarkBookEntity.toMarkBookMarkModel():MarkBookMarkModel{
    return MarkBookMarkModel(
       pagesSize = pagesSize,
    averageMarkALL= averageMarkALL, // 9.17
    markPage = markPage,
    number = number,// 25350033
    averageMark = averageMark,
    commonMark = commonMark, // 9.158940397350994
    commonRetakes=commonRetakes, // 0.013245033112582781
    date = date, // 24.12.2022
    formOfControl = formOfControl, // Диф.зачет
    fullSubject = fullSubject, // Логика
    hours = hours, // 108.0
    idFormOfControl = idFormOfControl, // 217
    idSubject = idSubject, // 20437
    mark = mark, // 7
    retakesCount = retakesCount, // 0
    subject = subject, // Логика
    teacher = teacher // Бархатков А. И.
    )
}