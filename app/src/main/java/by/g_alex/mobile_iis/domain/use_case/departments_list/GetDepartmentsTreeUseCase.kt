package by.g_alex.mobile_iis.domain.use_case.departments_list

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentsTreeDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDepartmentsTreeUseCase @Inject constructor(
    private val api: IisApiRepository
) {
    operator fun invoke(): Flow<Resource<List<DepartmentsTreeDto>>> = flow {
        try {
            emit(Resource.Loading<List<DepartmentsTreeDto>>())

            val data = api.getDepartmentsTree()
            emit(Resource.Success<List<DepartmentsTreeDto>>(data))

        } catch (_: HttpException) {

        } catch (_: IOException) {

        }
    }
}