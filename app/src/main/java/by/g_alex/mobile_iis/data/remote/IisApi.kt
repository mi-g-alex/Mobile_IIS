package by.g_alex.mobile_iis.data.remote

import by.g_alex.mobile_iis.data.local.entity.DormitoryDto
import by.g_alex.mobile_iis.data.local.entity.OmissionsByStudentDto
import by.g_alex.mobile_iis.data.local.entity.PrivilegesDto
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.data.remote.dto.department.DepartmentDto
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentEmployeesDto
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentsTreeDto
import by.g_alex.mobile_iis.data.remote.dto.departments.EmployeeDetailInfoDto
import by.g_alex.mobile_iis.data.remote.dto.diciplines.DiciplinesDto
import by.g_alex.mobile_iis.data.remote.dto.diploma.DiplomaDto
import by.g_alex.mobile_iis.data.remote.dto.diploma.PracticeDto
import by.g_alex.mobile_iis.data.remote.dto.employee.EmployeeListItemDto
import by.g_alex.mobile_iis.data.remote.dto.faculties.FacultiesDto
import by.g_alex.mobile_iis.data.remote.dto.grade_book.GradeBookDto
import by.g_alex.mobile_iis.data.remote.dto.group.GroupDtoItem
import by.g_alex.mobile_iis.data.remote.dto.login.*
import by.g_alex.mobile_iis.data.remote.dto.mark_book.MarkBookDto
import by.g_alex.mobile_iis.data.remote.dto.penalty.PenaltyDto
import by.g_alex.mobile_iis.data.remote.dto.personal_rating.PersonalRatingDto
import by.g_alex.mobile_iis.data.remote.dto.phone_book.PhoneSearchDto
import by.g_alex.mobile_iis.data.remote.dto.phone_book.RequestDto
import by.g_alex.mobile_iis.data.remote.dto.profile.PersonalCVDto
import by.g_alex.mobile_iis.data.remote.dto.rating.RatingDto
import by.g_alex.mobile_iis.data.remote.dto.schedule.MainDto
import by.g_alex.mobile_iis.data.remote.dto.settings.ChangePassDto
import by.g_alex.mobile_iis.data.remote.dto.settings.ConfirmEmailDto
import by.g_alex.mobile_iis.data.remote.dto.settings.ContactsDto
import by.g_alex.mobile_iis.data.remote.dto.settings.EmailChangeDto
import by.g_alex.mobile_iis.data.remote.dto.specialities.SpecialityDto
import by.g_alex.mobile_iis.data.remote.dto.students.StudentResponseDto
import by.g_alex.mobile_iis.data.remote.dto.students.StudentsRequestDto
import by.g_alex.mobile_iis.data.remote.dto.study.CertificatePlacesDto
import by.g_alex.mobile_iis.data.remote.dto.study.SendCertificateDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyApplicationsDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyCertificationsDto
import by.g_alex.mobile_iis.data.remote.dto.study.StudyMarkSheetDto
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetFocusThIdDto
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetSubjectsDto
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetTypesDto
import by.g_alex.mobile_iis.data.remote.dto.user_group.UserGroupDto
import by.g_alex.mobile_iis.domain.model.profile.PersonalCV
import by.g_alex.mobile_iis.domain.model.profile.Reference
import by.g_alex.mobile_iis.domain.model.profile.Skill
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface IisApi {

    // For AUTH

    @POST("api/v1/auth/login") // Авторизация
    fun loginToAccount(@Body request: LoginAndPasswordDto): Call<LoginResponseDto>

    @GET("api/v1/auth/logout") // Выход
    suspend fun logout(@Header("Cookie") cookieValue: String)

    @GET("api/v1/settings/masked-contacts?") // Получение списка контактов при восстановлении пароля
    fun restorePasswordEnterLogin(@Query("login") username: String): Call<RestorePasswordEnterLoginResponseDto>

    @POST("api/v1/settings/contact/exist") // Проверка на правильностть контакта
    fun restorePasswordCheckExist(@Body request: RestorePasswordCheckSendDto): Call<RestorePasswordEnterLoginResponseDto>

    @POST("api/v1/settings/password/reset") // Отпарвка кода восстановление
    fun restorePasswordGetCode(@Body request: RestorePasswordCheckSendDto): Call<Any>

    @POST("api/v1/settings/password/new") // Отпрвка нового пароля
    suspend fun restorePasswordApply(@Body request: RestorePasswordApplyDto): ResponseBody?

    // User Info
    @GET("api/v1/profiles/personal-cv") // Инфомарция о профиле / Главный экран
    suspend fun getProfilePersonCV(@Header("Cookie") cookieValue: String): PersonalCVDto

    @POST("api/v1/profiles/my-photo") // Смена фото
    fun updatePhoto(@Body body: RequestBody, @Header("Cookie") cookieValue: String): Call<String>

    @GET("api/v1/markbook") // Зачётка
    suspend fun getMarkBook(@Header("Cookie") cookieValue: String): MarkBookDto

    @GET("api/v1/student-groups/user-group-info") // Группа
    suspend fun getUserGroup(@Header("Cookie") cookieValue: String): UserGroupDto

    @GET("api/v1/grade-book") // Рейтинг
    suspend fun getGradeBook(@Header("Cookie") cookieValue: String): List<GradeBookDto>

    @GET("api/v1/omissions-by-student") // Пропуски
    suspend fun getOmissionsByStudent(@Header("Cookie") cookieValue: String): List<OmissionsByStudentDto>

    // Учёба
    @GET("api/v1/mark-sheet") // Ведомостички
    suspend fun getStudyMarkSheet(@Header("Cookie") cookieValue: String): List<StudyMarkSheetDto>

    @GET("api/v1/certificate") // Справки
    suspend fun getStudyCertificate(@Header("Cookie") cookieValue: String): List<StudyCertificationsDto>

    @GET("api/v1/lms/application-history") // ДОТ
    suspend fun getStudyApplications(@Header("Cookie") cookieValue: String): List<StudyApplicationsDto>

    @GET("api/v1/library/debts") // Задолженности библиотека
    suspend fun getStudyLibDebts(@Header("Cookie") cookieValue: String): List<String>

    @GET("api/v1/announcements") // Обьявления
    suspend fun getAnnouncements(@Header("Cookie") cookieValue: String): List<AnnouncemntDto>

    @GET("api/v1/dormitory-queue-application") // Общага / статус заселения
    suspend fun getDormitory(@Header("Cookie") cookieValue: String): List<DormitoryDto>

    @GET("api/v1/dormitory-queue-application/privileges") // Льготы
    suspend fun getPrivileges(@Header("Cookie") cookieValue: String): List<PrivilegesDto>

    @GET("api/v1/dormitory-queue-application/premium-penalty") // Взыскания
    suspend fun getPenalty(@Header("Cookie") cookieValue: String): List<PenaltyDto>

    @GET("api/v1/diploma-topic-suggestion/topics") // Список дипломов
    suspend fun getDiplomas(@Header("Cookie") cookieValue: String): List<DiplomaDto>

    @GET("api/v1/practice") // Список практики
    suspend fun getPractice(@Header("Cookie") cookieValue: String): List<PracticeDto>

    //Настройки
    @GET("api/v1/settings/contacts") // Список контактов
    suspend fun getContacts(@Header("Cookie") cookieValue: String): ContactsDto

    @POST("api/v1/settings/contact/update") // Обновление почты
    suspend fun updateEmail(
        @Header("Cookie") cookieValue: String,
        @Body email: EmailChangeDto
    ): ResponseBody?

    @POST("api/v1/settings/contact/send-confirm-message") // Отправка кода подтверждения
    suspend fun getCodeForEmail(@Header("Cookie") cookieValue: String, @Body id: Int): ResponseBody?

    @POST("api/v1/settings/contact/confirm") // Подтверждение кода
    suspend fun confirmCodeForEmail(
        @Header("Cookie") cookieValue: String,
        @Body email: ConfirmEmailDto
    ): ResponseBody?

    @POST("api/v1/settings/password/change") // Смена пароля из акка
    suspend fun changePass(
        @Header("Cookie") cookieValue: String,
        @Body password: ChangePassDto
    ): ResponseBody?

    @PUT("api/v1/profiles/personal-cv-searching-job") // Обновление галочки поиск работы
    suspend fun putJob(@Header("Cookie") cookieValue: String, @Body cvDto: PersonalCV)

    @PUT("api/v1/profiles/personal-cv-rating") // Обновление галочки просмотр рейтинга
    suspend fun putRating(@Header("Cookie") cookieValue: String, @Body cvDto: PersonalCV)

    @PUT("api/v1/profiles/personal-cv-published") // Обновление галочки просмотр профиля
    suspend fun putPublished(@Header("Cookie") cookieValue: String, @Body cvDto: PersonalCV)

    @PUT("api/v1/profiles/summary") // Обнлвление био
    suspend fun putSummary(@Header("Cookie") cookieValue: String, @Body cvDto: PersonalCV)

    @PUT("api/v1/profiles/my-references") // Обновление ссылок
    suspend fun putLinks(@Header("Cookie") cookieValue: String, @Body refs: List<Reference>)

    @POST("api/v1/profiles/my-skills") // Обновление навыков
    suspend fun postSkills(@Header("Cookie") cookieValue: String, @Body refs: List<Skill>)


    // For all
    @POST("api/v1/profiles") // Список студентов
    suspend fun getProfiles(@Body searchValue: StudentsRequestDto): StudentResponseDto

    @POST("api/v1/phone-book") // Поиск (телефонный справочник)
    suspend fun getPhoneNumber(@Body searchValue: RequestDto): PhoneSearchDto

    @GET("api/v1/schedule?") // Расписание группы
    suspend fun getSchedule(@Query("studentGroup") groupNumber: String): MainDto

    @GET("api/v1/employees/schedule/{urlId}") // Получние расписания преподавателя
    suspend fun getEmployeeSchedule(@Path("urlId") urlId: String): MainDto?

    @GET("api/v1/schedule/current-week") // Текущая неделя
    suspend fun getCurrentWeek(): Int

    @GET("api/v1/student-groups") // Группы студентов
    suspend fun getGroups(): List<GroupDtoItem>

    @GET("api/v1/employees/all") // Список преподавателей
    suspend fun getEmployeesList(): List<EmployeeListItemDto>

    @GET("api/v1/schedule/faculties") // Список факультетов
    suspend fun getFaculties(): List<FacultiesDto>

    @GET("api/v1/rating/specialities") // Список специальностей на факультете
    suspend fun getSpecialities(
        @Query("facultyId") id: Int,
        @Query("entryYear") year: Int
    ): List<SpecialityDto>

    @GET("api/v1/rating") // Получение общего рейта по году и специальности
    suspend fun getRating(@Query("year") year: Int, @Query("sdef") sdef: Int): List<RatingDto>


    @GET("api/v1/rating/studentRating") // Рейт по студаку
    suspend fun getPersonalRating(@Query("studentCardNumber") numb: String): PersonalRatingDto

    @GET("api/v1/list-disciplines") // Список дисцплин по специальности и году
    suspend fun getDisciplines(
        @Query("id") sdef: Int,
        @Query("year") year: Int
    ): List<DiciplinesDto>

    @GET("api/v1/departments") // Все кафедры
    suspend fun getDepartment(): List<DepartmentDto>

    @GET("api/v1/announcements/departments") // Анонсы кафедры
    suspend fun getDepartmentAnons(@Query("id") id: Int): List<AnnouncemntDto>

    @GET("api/v1/departments/tree") // Все подраздения
    suspend fun getDepartmentsTree(): List<DepartmentsTreeDto>

    @GET("api/v1/departments/name") // Имя подразделения по id
    suspend fun getDepartmentName(@Query("id") id: Int): String

    @GET("api/v1/employees") // Список сотрудников в подзраделении
    suspend fun getDepartmentEmployees(@Query("departmentId") id: Int): List<DepartmentEmployeesDto>

    @GET("api/v1/employees/details-url") // Подробная информация о сотруднике
    suspend fun getEmployeeDetailsInfo(@Query("urlId") id: String): EmployeeDetailInfoDto


    // Справки и Ведомостички заказ
    @GET("api/v1/certificate/places") // Места заказа справок
    suspend fun getCertificatePlaces(): List<CertificatePlacesDto>

    @POST("api/v1/certificate/register") // Заказать справку
    fun sendCertificate(
        @Body request: SendCertificateDto,
        @Header("Cookie") cookieValue: String
    ): Call<List<StudyCertificationsDto>>

    @GET("api/v1/certificate/close") // Отклонить справку
    suspend fun closeCertificate(@Query("id") id: Int, @Header("Cookie") cookieValue: String): Any

    @GET("api/v1/mark-sheet/types") // Типы ведомостичек
    suspend fun getMarkSheetTypes(): List<MarkSheetTypesDto>

    @GET("api/v1/mark-sheet/subjects") // Предметы ведомостичек
    suspend fun getMarkSheetSubjects(@Header("Cookie") cookieValue: String): List<MarkSheetSubjectsDto>

    @GET("api/v1/employees/mark-sheet") // поиск сотрудников для экзов
    suspend fun getMarkSheetFocusId(
        @Query("focsId") id: Int,
        @Header("Cookie") cookieValue: String
    ): List<MarkSheetFocusThIdDto>

    @GET("api/v1/employees/mark-sheet") // поиск сотрудников для лаб
    suspend fun getMarkSheetThId(
        @Query("thId") id: Int,
        @Header("Cookie") cookieValue: String
    ): List<MarkSheetFocusThIdDto>

    @GET("api/v1/employees/fio/requests") // поиск сотрудников по фио
    suspend fun findEmployeesByFio(@Query("employee-fio") fio: String): List<MarkSheetFocusThIdDto>


    // API для моих фидбеков
    @FormUrlEncoded
    @POST("https://docs.google.com/forms/d/e/1FAIpQLSekXyhCcihe83BvW2rEtzhGKOIWEDXtwMFs_3jPXGZF7a9Wxg/formResponse")
    suspend fun sendFeedback(
        @Field("entry.2075992135") problem: String,
        @Field("entry.1524800120") link: String?,
        @Field("fvv") fvv: Int = 1,
        @Field("partialResponse") partialResponse: String = "[null,null,\"1203676292478540819\"]",
        @Field("pageHistory") pageHistory: Int = 0,
        @Field("fbzx") fbzx: String = "1203676292478540819",
    ) : ResponseBody?
}