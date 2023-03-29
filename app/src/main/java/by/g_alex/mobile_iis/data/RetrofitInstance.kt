package by.g_alex.mobile_iis.data

import by.g_alex.mobile_iis.common.Constants.BASE_URL_IIS
import by.g_alex.mobile_iis.data.remote.IisApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy{
        Retrofit.Builder().baseUrl(BASE_URL_IIS).addConverterFactory(
            GsonConverterFactory.create()).build()
    }
    val api: IisApi by lazy{
        retrofit.create(IisApi::class.java)
    }
}