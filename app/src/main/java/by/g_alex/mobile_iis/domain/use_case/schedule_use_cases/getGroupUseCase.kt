package com.example.compose.domain.use_case

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.repository.ScheduleRepositoryIMPL
import com.example.compose.domain.model.GroupModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class getGroupUseCase {
    private val repository = ScheduleRepositoryIMPL()
    operator fun invoke(): Flow<Resource<List<GroupModel>>> = flow{
        try {
            emit(Resource.Loading())
            val sched = repository.getGroups()
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