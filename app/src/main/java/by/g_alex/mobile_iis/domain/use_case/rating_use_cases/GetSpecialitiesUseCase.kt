package by.g_alex.mobile_iis.domain.use_case.rating_use_cases

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.specialities.SpecialityDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSpecialitiesUseCase @Inject constructor(
    private val repository: IisApiRepository
) {
    operator fun invoke(id:Int,year:Int): Flow<Resource<List<SpecialityDto>>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.getSpecialities(id = id, year = year)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "ERROR"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Cringe no internet conection"))
        }
    }
}