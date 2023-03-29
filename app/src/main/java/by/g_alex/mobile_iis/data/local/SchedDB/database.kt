package com.example.compose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.compose.domain.model.IntegerListConverter
import com.example.compose.domain.model.LessonModel
import com.example.compose.domain.model.StringListConverter

@Database(entities = [LessonModel::class], version = 1)
@TypeConverters(
    StringListConverter::class,
    IntegerListConverter::class
)
abstract class database:RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
}