package by.g_alex.mobile_iis.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import by.g_alex.mobile_iis.common.Constants
import by.g_alex.mobile_iis.data.local.Converters
import by.g_alex.mobile_iis.data.local.UserDataBase
import by.g_alex.mobile_iis.data.remote.IisApi
import by.g_alex.mobile_iis.data.repository.IisApiRepositoryImpl
import by.g_alex.mobile_iis.data.repository.UserDataBaseRepositoryImpl
import by.g_alex.mobile_iis.data.util.GsonParser
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.schedule_use_cases.*
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideIisApi(): IisApi {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_IIS)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IisApi::class.java)
    }

    @Provides
    @Singleton
    fun providerLoginPassRepository(api: IisApi): IisApiRepository =
        IisApiRepositoryImpl(api)


    @Provides
    @Singleton
    fun provideScheduleViewModel(
        @ApplicationContext context: Context,
        getScheduleUseCase: GetScheduleUseCase,
        getCurrentWeekUseCase: GetCurrentWeekUseCase,
        getEmployeeScheduleUseCase: GetEmployeeScheduleUseCase,
        getGroupsUseCase: GetGroupsUseCase,
        getEmployeesListUseCase: GetEmployeesListUseCase,
        getExamsUseCase: GetExamsUseCase,
        db: UserDataBaseRepository
    ): ScheduleViewModel {
        return ScheduleViewModel(getScheduleUseCase = getScheduleUseCase,
        getCurrentWeekUseCase = getCurrentWeekUseCase,
            getEmployeeScheduleUseCase = getEmployeeScheduleUseCase,
            getGroupsUseCase = getGroupsUseCase,
            getEmployeesListUseCase = getEmployeesListUseCase,
            db = db,
            context = context,
            getExamsUseCase = getExamsUseCase
            )
    }

    @Provides
    @Singleton
    fun provideUserDataBase(app: Application): UserDataBase =
        Room.databaseBuilder(
            app,
            UserDataBase::class.java,
            UserDataBase.DATABASE_NAME
        )
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()


    @Provides
    @Singleton
    fun provideUserDataBaseRepository(dataBase: UserDataBase): UserDataBaseRepository =
        UserDataBaseRepositoryImpl(dataBase.userDao)


}