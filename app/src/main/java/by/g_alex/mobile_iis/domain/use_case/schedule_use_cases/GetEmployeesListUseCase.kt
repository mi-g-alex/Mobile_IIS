package by.g_alex.mobile_iis.domain.use_case.schedule_use_cases

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetEmployeesListUseCase @Inject constructor(
    private val repository: IisApiRepository,
) {
    operator fun invoke(): Flow<Resource<List<EmployeeModel>>> = flow {
        try {
            emit(Resource.Loading())
            val sched = repository.getEmployees()
            emit(Resource.Success(sched))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Http Error"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Cringe no internet connection"))
        }
    }
}