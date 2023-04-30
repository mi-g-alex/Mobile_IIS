package by.g_alex.mobile_iis.domain.use_case.get_profile.grade_book

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.model.profile.gradebook_model.GradeBookLessonModel
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class GetGradeBookUseCase@Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(): Flow<Resource<List<GradeBookLessonModel>>> = flow {
        try {
            emit(Resource.Loading<List<GradeBookLessonModel>>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<List<GradeBookLessonModel>>("LessCookie"))
            }
            if (cookie != null) {
                val data = api_repository.getGradeBook(cookie)
                db_repository.deleteGradeBooks()
                for (n in data) {
                    db_repository.insertGradeBook(n)
                }
                emit(Resource.Success<List<GradeBookLessonModel>>(data))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<List<GradeBookLessonModel>>("LessCookie"))
            } else {
                try {
                    val responseFromLogin =
                        api_repository.loginToAccount(
                            loginAndPassword.login.toString(),
                            loginAndPassword.password.toString()
                        )
                            .awaitResponse()
                    val cookie = responseFromLogin.headers()["Set-Cookie"].toString()
                    db_repository.setCookie(cookie)
                    val data = api_repository.getGradeBook(cookie)
                    db_repository.deleteGradeBooks()
                    for (n in data) {
                        db_repository.insertGradeBook(n)
                    }
                    emit(Resource.Success<List<GradeBookLessonModel>>(data))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<List<GradeBookLessonModel>>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<List<GradeBookLessonModel>>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {
            val data = db_repository.getGradeBook()
            emit(Resource.Success<List<GradeBookLessonModel>>(data))
        }
    }
}