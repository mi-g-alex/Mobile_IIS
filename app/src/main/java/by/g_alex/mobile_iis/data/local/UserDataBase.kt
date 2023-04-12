package by.g_alex.mobile_iis.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.g_alex.mobile_iis.data.local.entity.*


@Database(
    version = 8,
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
        AutoMigration (from = 7, to = 8)
    ]
)
@TypeConverters(Converters::class)
abstract class UserDataBase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "user_db"
    }
}