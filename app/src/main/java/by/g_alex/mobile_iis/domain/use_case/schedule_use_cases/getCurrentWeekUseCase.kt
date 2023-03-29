package com.example.compose.domain.use_case

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.repository.ScheduleRepositoryIMPL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class getCurrentWeekUseCase {
    private val repository = ScheduleRepositoryIMPL()
    operator fun invoke(): Flow<Resource<Int>> = flow{
        try {
            emit(Resource.Loading())
            val sched = repository.getCurrentWeek()
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