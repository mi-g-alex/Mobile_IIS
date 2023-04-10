package by.g_alex.mobile_iis.domain.use_case.get_profile.announcemnts

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.data.remote.dto.grade_book.GradeBookDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAnnouncementsUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(): Flow<Resource<List<AnnouncemntDto>>> = flow {
        try {
            emit(Resource.Loading<List<AnnouncemntDto>>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<List<AnnouncemntDto>>("LessCookie"))
            }
            if (cookie != null) {
                val data = api_repository.getAnnouncements(cookie)
                emit(Resource.Success<List<AnnouncemntDto>>(data))
            }
        } catch (e: HttpException) {

        } catch (_: IOException) {

        }
    }
}