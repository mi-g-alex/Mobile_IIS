package com.example.compose.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.compose.domain.model.LessonModel

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM LessonModel WHERE id LIKE :group")
    fun getAll(group:String): List<LessonModel>
    @Insert
    fun insertAll(vararg users: LessonModel)

}