package by.g_alex.mobile_iis.domain.use_case.department_schedule_use_case

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAnnouncementsByDepartment @Inject constructor(
    private val api_repository: IisApiRepository
) {
    operator fun invoke(id: Int): Flow<Resource<List<AnnouncemntDto>>> = flow {
        try {
            emit(Resource.Loading<List<AnnouncemntDto>>())

            val data = api_repository.getDepartmentAnons(id)
            emit(Resource.Success<List<AnnouncemntDto>>(data))

        } catch (_: HttpException) {


        } catch (_: IOException) {

        }
    }
}