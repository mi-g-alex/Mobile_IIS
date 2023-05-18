package by.g_alex.mobile_iis.domain.use_case.get_profile.study

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.study.CertificatePlacesDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCertificatePlacesUseCase @Inject constructor(
    private val api_repository: IisApiRepository
) {
    operator fun invoke(): Flow<Resource<List<CertificatePlacesDto>>> = flow {
        try {
            emit(Resource.Loading<List<CertificatePlacesDto>>())
            val data = api_repository.getCertificatePlaces()
            emit(Resource.Success<List<CertificatePlacesDto>>(data))
        } catch (_: HttpException) {
            emit(Resource.Error<List<CertificatePlacesDto>>("Internet"))
        } catch (_: IOException) {
            emit(Resource.Error<List<CertificatePlacesDto>>("Error"))
        }
    }
}