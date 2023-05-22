package by.g_alex.mobile_iis.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.g_alex.mobile_iis.data.local.entity.CookieEntity
import by.g_alex.mobile_iis.data.local.entity.DormitoryDto
import by.g_alex.mobile_iis.data.local.entity.GradeBookEntity
import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.local.entity.LoginAndPasswordEntity
import by.g_alex.mobile_iis.data.local.entity.MarkBookEntity
import by.g_alex.mobile_iis.data.local.entity.OmissionsByStudentDto
import by.g_alex.mobile_iis.data.local.entity.PenaltyModel
import by.g_alex.mobile_iis.data.local.entity.PrivilegesDto
import by.g_alex.mobile_iis.data.local.entity.ProfilePersonalCVEntity


@Database(
    version = 11,
    entities = [
        ProfilePersonalCVEntity::class,
        CookieEntity::class,
        LoginAndPasswordEntity::class,
        LessonModel::class,
        GradeBookEntity::class,
        MarkBookEntity::class,
        OmissionsByStudentDto::class,
        DormitoryDto::class,
        PrivilegesDto::class,
        PenaltyModel::class,
    ],
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 9, to = 10),
        AutoMigration(from = 10, to = 11)
    ],

    )
@TypeConverters(Converters::class)
abstract class UserDataBase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object {
        const val DATABASE_NAME = "user_db"
    }

}