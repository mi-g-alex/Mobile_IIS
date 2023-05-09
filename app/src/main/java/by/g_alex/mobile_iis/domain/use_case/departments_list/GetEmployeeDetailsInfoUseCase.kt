package by.g_alex.mobile_iis.domain.use_case.departments_list

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.departments.EmployeeDetailInfoDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetEmployeeDetailsInfoUseCase @Inject constructor(
    private val api: IisApiRepository
) {
    operator fun invoke(id: String): Flow<Resource<EmployeeDetailInfoDto>> = flow {
        try {
            emit(Resource.Loading<EmployeeDetailInfoDto>())

            val data = api.getEmployeeDetailsInfo(id)
            emit(Resource.Success<EmployeeDetailInfoDto>(data))

        } catch (_: HttpException) {

        } catch (_: IOException) {

        }
    }
}