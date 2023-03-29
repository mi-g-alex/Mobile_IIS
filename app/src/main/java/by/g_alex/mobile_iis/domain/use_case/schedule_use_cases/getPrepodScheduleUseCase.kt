package com.example.compose.domain.use_case

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.repository.ScheduleRepositoryIMPL
import com.example.compose.domain.model.LessonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class getPrepodScheduleUseCase {
    private val repository = ScheduleRepositoryIMPL()
    operator fun invoke(urlid:String): Flow<Resource<List<LessonModel>>> = flow{
        try {
            emit(Resource.Loading())
            val sched = repository.getPrepSchedule(urlid)
            emit(Resource.Success(sched))
        }
        catch (e: HttpException){
            emit(Resource.Error(e.localizedMessage?:"ERROR"))
        }
        catch (e: IOException){
            emit(Resource.Error(e.localizedMessage?:"Cringe no internet conection"))
        }
    }
}