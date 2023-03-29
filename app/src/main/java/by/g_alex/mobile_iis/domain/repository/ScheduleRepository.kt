package by.g_alex.mobile_iis.domain.repository

import com.example.compose.domain.model.GroupModel
import com.example.compose.domain.model.LessonModel
import com.example.compose.domain.model.PrepodModel

interface ScheduleRepository {
    suspend fun getSchedule(groupNum:String):List<LessonModel>
    suspend fun getCurrentWeek():Int

    suspend fun getGroups():List<GroupModel>

    suspend fun getPrepods():List<PrepodModel>

    suspend fun getPrepSchedule(urlid:String):List<LessonModel>
}