package by.g_alex.mobile_iis.domain.use_case.get_profile.study.mark_sheet

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetFocusThIdDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FindEmployeeByFioUseCase @Inject constructor(
    private val apiRepository: IisApiRepository
) {
    operator fun invoke(fio: String): Flow<Resource<List<MarkSheetFocusThIdDto>>> = flow {
        try {
            emit(Resource.Loading<List<MarkSheetFocusThIdDto>>())
            val data = apiRepository.findEmployeesByFio(fio)
            emit(Resource.Success<List<MarkSheetFocusThIdDto>>(data))
        } catch (_: HttpException) {
            emit(Resource.Error<List<MarkSheetFocusThIdDto>>("Internet"))
        } catch (_: IOException) {
            emit(Resource.Error<List<MarkSheetFocusThIdDto>>("Error"))
        }
    }
}