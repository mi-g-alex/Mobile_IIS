package by.g_alex.mobile_iis.di

import android.app.Application
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
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
        interceptor.level = HttpLoggingInterceptor.Level.BASIC

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