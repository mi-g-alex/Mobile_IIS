package by.g_alex.mobile_iis.data.repository

import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.remote.IisApi
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.data.remote.dto.dormitory.DormitoryDto
import by.g_alex.mobile_iis.data.remote.dto.dormitory.PrivilegesDto
import by.g_alex.mobile_iis.data.remote.dto.employee.toEmployeeModel
import by.g_alex.mobile_iis.data.remote.dto.faculties.FacultiesDto
import by.g_alex.mobile_iis.data.remote.dto.grade_book.toGradeBookLessonModel
import by.g_alex.mobile_iis.data.remote.dto.group.toGroupModel
import by.g_alex.mobile_iis.data.remote.dto.login.LoginAndPasswordDto
import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordApplyDto
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordCheckSendDto
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.mark_book.toListMarkBookMarkModel
import by.g_alex.mobile_iis.data.remote.dto.omissions.OmissionsByStudentDto
import by.g_alex.mobile_iis.data.remote.dto.penalty.toPenltyModel
import by.g_alex.mobile_iis.data.remote.dto.personal_rating.PersonalRatingDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
import by.g_alex.mobile_iis.data.remote.dto.rating.RatingDto
import by.g_alex.mobile_iis.data.remote.dto.settings.ConfirmEmailDto
import by.g_alex.mobile_iis.data.remote.dto.settings.ContactsDto
import by.g_alex.mobile_iis.data.remote.dto.settings.EmailChangeDto
import by.g_alex.mobile_iis.data.remote.dto.specialities.SpecialityDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyDto
import by.g_alex.mobile_iis.data.remote.dto.use_group.UserGroupDto
import by.g_alex.mobile_iis.domain.model.profile.PersonalCV
import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel
import by.g_alex.mobile_iis.domain.model.profile.penalty_model.PenaltyModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.GroupModel
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.awaitResponse
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

    override suspend fun logout(token: String) {
        api.logout(token)
    }

    override suspend fun restorePasswordEnterLogin(username: String): RestorePasswordEnterLoginResponseDto? {
        val s = api.restorePasswordEnterLogin(username).awaitResponse()
        if (s.isSuccessful) {
            return s.body()
        }
        return null
    }

    override suspend fun getPrivileges(token: String): List<PrivilegesDto> {
        return api.getPrivileges(token)
    }

    override suspend fun restorePasswordCheckExist(
        login: String,
        contactValue: String
    ): RestorePasswordEnterLoginResponseDto? {
        val s = api.restorePasswordCheckExist(RestorePasswordCheckSendDto(login, contactValue))
            .awaitResponse()
        if (s.isSuccessful) {
            return s.body()
        }
        return null
    }

    override suspend fun restorePasswordGetCode(login: String, contactValue: String) {
        api.restorePasswordGetCode(RestorePasswordCheckSendDto(login, contactValue)).awaitResponse()
    }

    override suspend fun restorePasswordApply(
        login: String,
        password: String,
        contactValue: String,
        code: String
    ): Boolean {
        val s =
            api.restorePasswordApply(RestorePasswordApplyDto(login, contactValue, code, password))
                .awaitResponse()
        return s.isSuccessful
    }

    override suspend fun getProfilePersonalCV(token: String): PersonalCVDto {
        return api.getProfilePersonCV(token)
    }

    override suspend fun updatePhoto(request: String, token: String): Call<String> {
        val requestBody = request.toRequestBody("text/plain".toMediaType())
        return api.updatePhoto(requestBody, token)
    }

    override suspend fun getGradeBook(cookie: String): List<GradeBookLessonModel> {
        return api.getGradeBook(cookie)[0].toGradeBookLessonModel()
    }

    override suspend fun getMarkBook(token: String): List<MarkBookMarkModel> {
        return api.getMarkBook(token).toListMarkBookMarkModel()
    }

    override suspend fun getUserGroup(token: String): UserGroupDto {
        return api.getUserGroup(token)
    }

    override suspend fun getDormitory(token: String): List<DormitoryDto> {
        return api.getDormitory(token)
    }

    override suspend fun getPenalty(token: String): List<PenaltyModel> {
        val list = mutableListOf<PenaltyModel>()
        api.getPenalty(token).onEach {
            list.add(it.toPenltyModel())
        }
        return list
    }

    override suspend fun getOmissionsByStudent(token: String): List<OmissionsByStudentDto> {
        return api.getOmissionsByStudent(token)
    }

    override suspend fun getStudy(token: String): StudyDto {
        return StudyDto(
            api.getStudyApplications(token),
            api.getStudyCertificate(token),
            api.getStudyMarkSheet(token),
            api.getStudyLibDebts(token)
        )
    }

    override suspend fun getAnnouncements(token: String): List<AnnouncemntDto> {
        return api.getAnnouncements(token)
    }

    override suspend fun getEmail(token: String): ContactsDto {
        return api.getContacts(token)
    }

    override suspend fun updateEmail(token: String, email: EmailChangeDto): Call<Any?> {
        return api.updateEmail(token, email)
    }

    override suspend fun getCodeForEmail(token: String, id: Int): Call<Any> {
        return api.getCodeForEmail(token, id)
    }

    override suspend fun confirmCodeForEmail(token: String, email: ConfirmEmailDto): Call<Any> {
        return api.confirmCodeForEmail(token, email)
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

    override suspend fun getFaculties(): List<FacultiesDto> {
        return api.getFaculties()
    }

    override suspend fun getSpecialities(year: Int, id: Int): List<SpecialityDto> {
        return api.getSpecialities(id,year)
    }

    override suspend fun getRating(year: Int, id: Int): List<RatingDto> {
        return api.getRating(year,id)
    }

    override suspend fun getPersonalRating(number: String): PersonalRatingDto {
        return api.getPersonalRating(number)
    }

    override suspend fun putPublished(token:String,cvDto: PersonalCV){
        api.putPublished(token,cvDto)
    }

    override suspend fun putRating(token: String, cvDto: PersonalCV) {
        api.putRating(token,cvDto)
    }

    override suspend fun putJob(token: String, cvDto: PersonalCV) {
        api.putJob(token,cvDto)
    }
}