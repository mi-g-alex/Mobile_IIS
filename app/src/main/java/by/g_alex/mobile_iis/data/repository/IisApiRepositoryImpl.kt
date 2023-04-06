package by.g_alex.mobile_iis.data.repository

import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.remote.IisApi
import by.g_alex.mobile_iis.data.remote.dto.employee.toEmployeeModel
import by.g_alex.mobile_iis.data.remote.dto.group.toGroupModel
import by.g_alex.mobile_iis.data.remote.dto.login.LoginAndPasswordDto
import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.mark_book.MarkBookDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
import by.g_alex.mobile_iis.data.remote.dto.use_group.UserGroupDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.GroupModel
import retrofit2.Call
import javax.inject.Inject

class IisApiRepositoryImpl @Inject constructor(
    private val api: IisApi
) : IisApiRepository {

    override suspend fun loginToAccount(
        username: String,
        password: String
    ): Call<LoginResponseDto> {
        val a = LoginAndPasswordDto(username, password)
        return api.loginToAccount(a)
    }

    override suspend fun getProfilePersonalCV(token: String): PersonalCVDto {
        return api.getProfilePersonCV(token)
    }

    override suspend fun logout(token: String) {
        api.logout(token)
    }

    override suspend fun getMarkBook(token: String) : MarkBookDto {
        return api.getMarkBook(token)
    }

    override suspend fun getUserGroup(token: String): UserGroupDto {
        return api.getUserGroup(token)
    }

    override suspend fun getSchedule(groupNum: String): List<LessonModel> {
        return api.getSchedule(groupNum).toLessonList(true)
    }

    override suspend fun getCurrentWeek(): Int {
        return api.getCurrentWeek()
    }

    override suspend fun getGroups(): List<GroupModel> {
        return api.getGroups().map { item -> item.toGroupModel() }
    }

    override suspend fun getEmployeeSchedule(urlId: String): List<LessonModel> {
        return api.getEmployeeSchedule(urlId)?.toLessonList(false) ?: emptyList()
    }

    override suspend fun getEmployees(): List<EmployeeModel> {
        return api.getEmployeesList().map { item -> item.toEmployeeModel() }
    }
}