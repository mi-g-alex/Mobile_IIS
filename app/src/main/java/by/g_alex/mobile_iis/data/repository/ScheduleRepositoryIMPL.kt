package by.g_alex.mobile_iis.data.repository

import android.util.Log
import by.g_alex.mobile_iis.data.RetrofitInstance
import by.g_alex.mobile_iis.domain.repository.ScheduleRepository
import com.example.compose.data.remote.dto.group.toGroupModel
import com.example.compose.data.remote.dto.Schedule.toLessonList
import com.example.compose.data.remote.dto.prepod.toPrepodModel
import com.example.compose.data.remote.dto.prepodSchedule.toLessonList
import com.example.compose.domain.model.GroupModel
import com.example.compose.domain.model.LessonModel
import com.example.compose.domain.model.PrepodModel


class ScheduleRepositoryIMPL: ScheduleRepository {
    override suspend fun getSchedule(groupNum:String): List<LessonModel> {
       // Log.e("TAg",RetrofitInstance.api.getShedule(groupNum).startDate)
        val list =  RetrofitInstance.api.getShedule(groupNum)
        Log.e("GG",list.studentGroupDto.name)
        return list.toLessonList()
    }
    override suspend fun getPrepSchedule(urlid:String): List<LessonModel> {
        // Log.e("TAg",RetrofitInstance.api.getShedule(groupNum).startDate)
        val list =  RetrofitInstance.api.getPrepShedule(urlid)
        Log.e("GG",list.employeeDto.lastName)
        return list.toLessonList()
    }

    override suspend fun getPrepods(): List<PrepodModel> {
        val ml : MutableList<PrepodModel> = mutableListOf();
        for(a in RetrofitInstance.api.getPrepod()){
            ml.add(a.toPrepodModel())
        }
        Log.e("sdfsdfsd",ml[0].fio)
        return ml
    }

    override suspend fun getGroups(): List<GroupModel> {
        val ml : MutableList<GroupModel> = mutableListOf();
        for(a in RetrofitInstance.api.getGroups()){
            ml.add(a.toGroupModel())
        }
        Log.e("sdfsdfsd",ml[0].specialityName)
        return ml
    }
    override suspend fun getCurrentWeek(): Int {
        val curWeek = RetrofitInstance.api.getCurrentWeek()
        Log.e("DFGHJKL",curWeek.toString())
        return curWeek
    }
}