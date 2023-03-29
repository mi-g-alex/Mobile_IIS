package by.g_alex.mobile_iis.data.repository

import by.g_alex.mobile_iis.data.local.UserDao
import by.g_alex.mobile_iis.data.local.entity.CookieEntity
import by.g_alex.mobile_iis.data.local.entity.LoginAndPasswordEntity
import by.g_alex.mobile_iis.domain.model.profile.PersonalCV
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository

class UserDataBaseRepositoryImpl(
    private val dao : UserDao
) : UserDataBaseRepository {


    override suspend fun getCookie(): String? {
        return dao.getCookie()?.cookie
    }

    override suspend fun setCookie(cookie : String?) {
        dao.setCookie(CookieEntity(0, cookie))
    }

    override suspend fun deleteCookie() {
        dao.deleteCookie()
    }

    override suspend fun getLoginAndPassword(): LoginAndPasswordEntity? {
        return dao.getLoginAndPassword()
    }

    override suspend fun setLoginAndPassword(username : String, password : String) {
        dao.setLoginAndPassword(LoginAndPasswordEntity(0, username, password))
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
}