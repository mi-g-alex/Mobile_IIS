package by.g_alex.mobile_iis.data.repository

import by.g_alex.mobile_iis.data.local.entity.DormitoryDto
import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.local.entity.OmissionsByStudentDto
import by.g_alex.mobile_iis.data.local.entity.PenaltyModel
import by.g_alex.mobile_iis.data.local.entity.PrivilegesDto
import by.g_alex.mobile_iis.data.remote.IisApi
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.data.remote.dto.department.DepartmentDto
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentEmployeesDto
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentsTreeDto
import by.g_alex.mobile_iis.data.remote.dto.departments.EmployeeDetailInfoDto
import by.g_alex.mobile_iis.data.remote.dto.diciplines.DiciplinesDto
import by.g_alex.mobile_iis.data.remote.dto.diploma.DiplomaDto
import by.g_alex.mobile_iis.data.remote.dto.diploma.PracticeDto
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
import by.g_alex.mobile_iis.data.remote.dto.penalty.toPenltyModel
import by.g_alex.mobile_iis.data.remote.dto.personal_rating.PersonalRatingDto
import by.g_alex.mobile_iis.data.remote.dto.phone_book.PhoneSearchDto
import by.g_alex.mobile_iis.data.remote.dto.phone_book.RequestDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
import by.g_alex.mobile_iis.data.remote.dto.rating.RatingDto
import by.g_alex.mobile_iis.data.remote.dto.settings.ChangePassDto
import by.g_alex.mobile_iis.data.remote.dto.settings.ConfirmEmailDto
import by.g_alex.mobile_iis.data.remote.dto.settings.ContactsDto
import by.g_alex.mobile_iis.data.remote.dto.settings.EmailChangeDto
import by.g_alex.mobile_iis.data.remote.dto.specialities.SpecialityDto
import by.g_alex.mobile_iis.data.remote.dto.students.StudentResponseDto
import by.g_alex.mobile_iis.data.remote.dto.students.StudentsRequestDto
import by.g_alex.mobile_iis.data.remote.dto.study.CertificatePlacesDto
import by.g_alex.mobile_iis.data.remote.dto.study.SendCertificateDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyCertificationsDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyDto
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetFocusThIdDto
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetSubjectsDto
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetTypesDto
import by.g_alex.mobile_iis.data.remote.dto.user_group.UserGroupDto
import by.g_alex.mobile_iis.domain.model.profile.PersonalCV
import by.g_alex.mobile_iis.domain.model.profile.Reference
import by.g_alex.mobile_iis.domain.model.profile.Skill
import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.GroupModel
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
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
    ): ResponseBody? {
        return api.restorePasswordApply(
            RestorePasswordApplyDto(
                login,
                contactValue,
                code,
                password
            )
        )
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

    override suspend fun getDiplomas(token: String): List<DiplomaDto> {
        return api.getDiplomas(token)
    }

    override suspend fun getPractice(token: String): List<PracticeDto> {
        return api.getPractice(token)
    }

    override suspend fun getEmail(token: String): ContactsDto {
        return api.getContacts(token)
    }

    override suspend fun updateEmail(token: String, email: EmailChangeDto): ResponseBody? {
        return api.updateEmail(token, email)
    }

    override suspend fun getCodeForEmail(token: String, id: Int): ResponseBody? {
        return api.getCodeForEmail(token, id)
    }

    override suspend fun confirmCodeForEmail(token: String, email: ConfirmEmailDto): ResponseBody? {
        return api.confirmCodeForEmail(token, email)
    }

    override suspend fun changePass(
        token: String,
        password: String,
        newPassword: String
    ): ResponseBody? {
        return api.changePass(token, ChangePassDto(password, newPassword))
    }

    override suspend fun getSchedule(groupNum: String): List<LessonModel> {
        return api.getSchedule(groupNum).toLessonList(true)
    }

    override suspend fun getExams(groupNum: String): List<LessonModel>? {
       if(groupNum[0].isDigit()) {
           return api.getSchedule(groupNum).toExamList()
       }
        else{
            return api.getEmployeeSchedule(groupNum)?.toExamList()
       }
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
        return api.getSpecialities(id, year)
    }

    override suspend fun getRating(year: Int, id: Int): List<RatingDto> {
        return api.getRating(year, id)
    }

    override suspend fun getPersonalRating(number: String): PersonalRatingDto {
        return api.getPersonalRating(number)
    }

    override suspend fun putPublished(token: String, cvDto: PersonalCV) {
        api.putPublished(token, cvDto)
    }

    override suspend fun putRating(token: String, cvDto: PersonalCV) {
        api.putRating(token, cvDto)
    }

    override suspend fun putJob(token: String, cvDto: PersonalCV) {
        api.putJob(token, cvDto)
    }

    override suspend fun putSummary(token: String, cvDto: PersonalCV) {
        api.putSummary(token, cvDto)
    }

    override suspend fun putLinks(token: String, refs: List<Reference>) {
        api.putLinks(token, refs)
    }

    override suspend fun postSkills(token: String, skills: List<Skill>) {
        api.postSkills(token, skills)
    }

    override suspend fun getDisciplines(id: Int, year: Int): List<DiciplinesDto> {
        return api.getDisciplines(year = year, sdef = id)
    }

    override suspend fun getDepartments(): List<DepartmentDto> {
        return api.getDepartment()
    }

    override suspend fun getDepartmentAnons(id: Int): List<AnnouncemntDto> {
        return api.getDepartmentAnons(id)
    }

    override suspend fun getPhone(value: RequestDto): PhoneSearchDto {
        return api.getPhoneNumber(value)
    }

    override suspend fun getDepartmentsTree(): List<DepartmentsTreeDto> {
        return api.getDepartmentsTree()
    }

    override suspend fun getDepartmentName(id: Int): String {
        return api.getDepartmentName(id)
    }

    override suspend fun getStudentProfiles(value: StudentsRequestDto): StudentResponseDto {
        return api.getProfiles(value)
    }

    override suspend fun getDepartmentEmployees(id: Int): List<DepartmentEmployeesDto> {
        return api.getDepartmentEmployees(id)
    }

    override suspend fun getEmployeeDetailsInfo(id: String): EmployeeDetailInfoDto {
        return api.getEmployeeDetailsInfo(id)
    }

    override suspend fun getCertificatePlaces(): List<CertificatePlacesDto> {
        return api.getCertificatePlaces()
    }

    override suspend fun sendCertificate(
        request: SendCertificateDto,
        token: String
    ): Call<List<StudyCertificationsDto>> {
        return api.sendCertificate(request, token)
    }

    override suspend fun closeCertificate(id: Int, token: String): Any {
        return api.closeCertificate(id, token)
    }

    override suspend fun getMarkSheetTypes(): List<MarkSheetTypesDto> {
        return api.getMarkSheetTypes()
    }

    override suspend fun getMarkSheetSubjects(token: String): List<MarkSheetSubjectsDto> {
        return api.getMarkSheetSubjects(token)
    }

    override suspend fun getMarkSheetById(
        focusId: Int?,
        thId: Int?,
        token: String
    ): List<MarkSheetFocusThIdDto> {
        if(focusId != null) {
            return api.getMarkSheetFocusId(focusId, token)
        } else if(thId != null) {
            return api.getMarkSheetThId(thId, token)
        }
        return emptyList()
    }

    override suspend fun findEmployeesByFio(fio: String): List<MarkSheetFocusThIdDto> {
        return api.findEmployeesByFio(fio)
    }

    override suspend fun sendFeedback(problem: String, link: String?) : ResponseBody? {
        return api.sendFeedback(problem = problem, link = link)
    }
}