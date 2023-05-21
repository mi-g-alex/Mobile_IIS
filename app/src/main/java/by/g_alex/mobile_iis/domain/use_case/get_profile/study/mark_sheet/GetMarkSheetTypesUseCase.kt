package by.g_alex.mobile_iis.domain.use_case.get_profile.study.mark_sheet

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetTypesDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMarkSheetTypesUseCase @Inject constructor(
    private val apiRepository: IisApiRepository
) {
    operator fun invoke(): Flow<Resource<List<MarkSheetTypesDto>>> = flow {
        try {
            emit(Resource.Loading<List<MarkSheetTypesDto>>())
            val data = apiRepository.getMarkSheetTypes()
            emit(Resource.Success<List<MarkSheetTypesDto>>(data))
        } catch (_: HttpException) {
            emit(Resource.Error<List<MarkSheetTypesDto>>("Internet"))
        } catch (_: IOException) {
            emit(Resource.Error<List<MarkSheetTypesDto>>("Error"))
        }
    }
}