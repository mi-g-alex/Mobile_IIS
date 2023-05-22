package by.g_alex.mobile_iis.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.g_alex.mobile_iis.data.local.entity.CookieEntity
import by.g_alex.mobile_iis.data.local.entity.GradeBookEntity
import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.local.entity.LoginAndPasswordEntity
import by.g_alex.mobile_iis.data.local.entity.MarkBookEntity
import by.g_alex.mobile_iis.data.local.entity.ProfilePersonalCVEntity


@Database(
    version = 10,
    entities = [
        ProfilePersonalCVEntity::class,
        CookieEntity::class,
        LoginAndPasswordEntity::class,
        LessonModel::class,
        GradeBookEntity::class,
        MarkBookEntity::class
    ],
    exportSchema = true,
      autoMigrations = [
          AutoMigration(from = 9, to = 10)
      ],

)
@TypeConverters(Converters::class)
abstract class UserDataBase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "user_db"
    }

}