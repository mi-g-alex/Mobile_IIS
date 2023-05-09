package by.g_alex.mobile_iis.domain.repository

import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.data.remote.dto.department.DepartmentDto
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentEmployeesDto
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentsTreeDto
import by.g_alex.mobile_iis.data.remote.dto.departments.EmployeeDetailInfoDto
import by.g_alex.mobile_iis.data.remote.dto.diciplines.DiciplinesDto
import by.g_alex.mobile_iis.data.remote.dto.diploma.DiplomaDto
import by.g_alex.mobile_iis.data.remote.dto.diploma.PracticeDto
import by.g_alex.mobile_iis.data.remote.dto.dormitory.DormitoryDto
import by.g_alex.mobile_iis.data.remote.dto.dormitory.PrivilegesDto
import by.g_alex.mobile_iis.data.remote.dto.faculties.FacultiesDto
import by.g_alex.mobile_iis.data.remote.dto.login.LoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.login.RestorePasswordEnterLoginResponseDto
import by.g_alex.mobile_iis.data.remote.dto.omissions.OmissionsByStudentDto
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
import by.g_alex.mobile_iis.domain.model.profile.Reference
import by.g_alex.mobile_iis.domain.model.profile.Skill
import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel
import by.g_alex.mobile_iis.domain.model.profile.penalty_model.PenaltyModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.GroupModel
import okhttp3.ResponseBody
import retrofit2.Call

interface IisApiRepository {

    // For AUTH
    suspend fun loginToAccount(
        username: String,
        password: String
    ): Call<LoginResponseDto>

    suspend fun logout(token: String)

    suspend fun restorePasswordEnterLogin(username: String): RestorePasswordEnterLoginResponseDto?

    suspend fun restorePasswordCheckExist(
        login: String,
        contactValue: String
    ): RestorePasswordEnterLoginResponseDto?

    suspend fun restorePasswordGetCode(login: String, contactValue: String)

    suspend fun restorePasswordApply(
        login: String,
        password: String,
        contactValue: String,
        code: String
    ): ResponseBody?

    // For USER
    suspend fun getProfilePersonalCV(token: String): PersonalCVDto

    suspend fun updatePhoto(request: String, token: String): Call<String>

    suspend fun getGradeBook(cookie: String): List<GradeBookLessonModel>

    suspend fun getMarkBook(token: String): List<MarkBookMarkModel>

    suspend fun getUserGroup(token: String): UserGroupDto
    suspend fun getOmissionsByStudent(token: String): List<OmissionsByStudentDto>

    suspend fun getStudy(token: String): StudyDto

    suspend fun getAnnouncements(token: String): List<AnnouncemntDto>

    suspend fun getDormitory(token: String): List<DormitoryDto>

    suspend fun getPrivileges(token: String): List<PrivilegesDto>

    suspend fun getPenalty(token: String): List<PenaltyModel>

    suspend fun getDiplomas(token: String): List<DiplomaDto>

    suspend fun getPractice(token: String): List<PracticeDto>

    suspend fun getEmail(token: String): ContactsDto

    suspend fun updateEmail(token: String, email: EmailChangeDto): ResponseBody?

    suspend fun getCodeForEmail(token: String, id: Int): ResponseBody?

    suspend fun confirmCodeForEmail(token: String, email: ConfirmEmailDto): ResponseBody?

    suspend fun changePass(token: String, password: String, newPassword: String): ResponseBody?

    suspend fun putPublished(token: String, cvDto: PersonalCV)

    suspend fun putJob(token: String, cvDto: PersonalCV)

    suspend fun putRating(token: String, cvDto: PersonalCV)

    suspend fun putSummary(token: String, cvDto: PersonalCV)

    suspend fun putLinks(token: String, refs: List<Reference>)

    suspend fun postSkills(token: String, skills: List<Skill>)

    // For All
    suspend fun getSchedule(groupNum: String): List<LessonModel>?

    suspend fun getCurrentWeek(): Int

    suspend fun getGroups(): List<GroupModel>

    suspend fun getEmployees(): List<EmployeeModel>

    suspend fun getEmployeeSchedule(urlId: String): List<LessonModel>

    suspend fun getFaculties(): List<FacultiesDto>

    suspend fun getSpecialities(year: Int, id: Int): List<SpecialityDto>

    suspend fun getRating(year: Int, id: Int): List<RatingDto>

    suspend fun getPersonalRating(number: String): PersonalRatingDto

    suspend fun getDiciplines(id: Int, year: Int): List<DiciplinesDto>

    suspend fun getDepartments(): List<DepartmentDto>

    suspend fun getDepartmentAnons(id: Int): List<AnnouncemntDto>

    suspend fun getDepartmentsTree(): List<DepartmentsTreeDto>

    suspend fun getDepartmentName(id: Int): String?

    suspend fun getDepartmentEmployees(id: Int): List<DepartmentEmployeesDto>

    suspend fun getEmployeeDetailsInfo(id: String) : EmployeeDetailInfoDto
}