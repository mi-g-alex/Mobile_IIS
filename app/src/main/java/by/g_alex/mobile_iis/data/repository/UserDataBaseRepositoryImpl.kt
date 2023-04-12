package by.g_alex.mobile_iis.data.repository

import by.g_alex.mobile_iis.data.local.UserDao
import by.g_alex.mobile_iis.data.local.entity.*
import by.g_alex.mobile_iis.domain.model.profile.PersonalCV
import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel
import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.toGradeBookEntity
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.toMarkBookEntity
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository

class UserDataBaseRepositoryImpl(
    private val dao: UserDao
) : UserDataBaseRepository {

    override suspend fun getCookie(): String? {
        return dao.getCookie()?.cookie
    }

    override suspend fun setCookie(cookie: String?) {
        dao.setCookie(CookieEntity(0, cookie))
    }

    override suspend fun deleteCookie() {
        dao.deleteCookie()
    }

    override suspend fun getLoginAndPassword(): LoginAndPasswordEntity? {
        return dao.getLoginAndPassword()
    }

    override suspend fun setLoginAndPassword(username: String, password: String) {
        dao.setLoginAndPassword(LoginAndPasswordEntity(0, username, password))
    }

    override suspend fun getMarkBooks(): List<MarkBookMarkModel> {
        val marklist = mutableListOf<MarkBookMarkModel>()
        dao.getMarkBook().onEach {
            marklist.add(it.toMarkBookMarkModel())
        }
        return marklist
    }
    override suspend fun deleteMarkBooks() {
        dao.deleteMarkbooks()
    }
    override suspend fun insertMarkBook(markbook: MarkBookMarkModel) {
        dao.insertMarkBook(markbook.toMarkBookEntity())
    }
    override suspend fun getGradeBook(): List<GradeBookLessonModel> {
        val gradelist = mutableListOf<GradeBookLessonModel>()
        dao.getGradeBook().onEach {
            gradelist.add(it.toGradeBookLessonModel())
        }
        return gradelist
    }

    override suspend fun deleteGradeBooks() {
        dao.deleteGradebooks()
    }
    override suspend fun insertGradeBook(gradebook: GradeBookLessonModel) {
        dao.insertGradeBook(gradebook.toGradeBookEntity())
    }
    override suspend fun deleteLoginAndPassword() {
        dao.deleteLoginAndPassword()
    }

    override suspend fun getProfilePersonalCV(): PersonalCV? {
        return dao.getProfilePersonalCV()?.toModel()
    }

    override suspend fun setProfilePersonalCV(personalCV: PersonalCV) {
        dao.setProfilePersonalCV(personalCV.toEntity())
    }

    override suspend fun deleteProfilePersonalCV() {
        dao.deleteProfilePersonalCV()
    }

    override suspend fun deleteSchedulebyName(name: String) {
        dao.deleteSchedulebyName(name)
    }
    override suspend fun getSchedule(group: String): List<LessonModel> {
        return dao.getSchedule(group);
    }

    override suspend fun insertSchedule(users: LessonModel) {
        dao.insertSchedule(users)
    }
}