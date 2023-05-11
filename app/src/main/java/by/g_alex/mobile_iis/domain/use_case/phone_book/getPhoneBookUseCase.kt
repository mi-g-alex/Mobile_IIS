package by.g_alex.mobile_iis.domain.use_case.phone_book

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.phone_book.PhoneSearchDto
import by.g_alex.mobile_iis.data.remote.dto.phone_book.RequestDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class getPhoneBookUseCase @Inject constructor(
    private val api: IisApiRepository
) {
    operator fun invoke(value:RequestDto): Flow<Resource<PhoneSearchDto>> = flow {
        try {
            emit(Resource.Loading<PhoneSearchDto>())

            val data = api.getPhone(value)
            emit(Resource.Success<PhoneSearchDto>(data))

        } catch (_: HttpException) {

        } catch (_: IOException) {

        }
    }
}