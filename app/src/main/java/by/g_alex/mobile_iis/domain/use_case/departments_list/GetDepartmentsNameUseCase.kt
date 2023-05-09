package by.g_alex.mobile_iis.domain.use_case.departments_list

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDepartmentsNameUseCase @Inject constructor(
    private val api: IisApiRepository
) {
    operator fun invoke(id: Int): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())

            val data = api.getDepartmentName(id)
            emit(Resource.Success<String>(data))

        } catch (_: HttpException) {

        } catch (_: IOException) {

        }
    }
}