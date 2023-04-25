package by.g_alex.mobile_iis.domain.use_case.rating_use_cases

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.faculties.FacultiesDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFacultiesUseCase @Inject constructor(
    private val repository: IisApiRepository
) {
    operator fun invoke(): Flow<Resource<List<FacultiesDto>>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.getFaculties()
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "ERROR"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Cringe no internet conection"))
        }
    }
}