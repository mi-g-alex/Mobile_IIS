package by.g_alex.mobile_iis.domain.use_case.students_use_case

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.students.StudentResponseDto
import by.g_alex.mobile_iis.data.remote.dto.students.StudentsRequestDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetStudentProfileUseCase @Inject constructor(
    private val repository: IisApiRepository
) {
    operator fun invoke(value:StudentsRequestDto): Flow<Resource<StudentResponseDto>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.getStudentProfiles(value)
            emit(Resource.Success(data))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "ERROR"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "Cringe no internet conection"))
        }
    }
}