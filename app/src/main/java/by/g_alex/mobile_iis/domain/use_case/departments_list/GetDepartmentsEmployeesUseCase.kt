package by.g_alex.mobile_iis.domain.use_case.departments_list

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentEmployeesDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDepartmentsEmployeesUseCase @Inject constructor(
    private val api: IisApiRepository
) {
    operator fun invoke(id: Int): Flow<Resource<List<DepartmentEmployeesDto>>> = flow {
        try {
            emit(Resource.Loading<List<DepartmentEmployeesDto>>())

            val data = api.getDepartmentEmployees(id)
            emit(Resource.Success<List<DepartmentEmployeesDto>>(data))

        } catch (_: HttpException) {

        } catch (_: IOException) {

        }
    }
}