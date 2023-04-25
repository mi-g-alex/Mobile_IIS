package by.g_alex.mobile_iis.domain.use_case.get_profile.settings

import android.util.Log
import androidx.compose.animation.rememberSplineBasedDecay
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.settings.EmailChangeDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.await
import retrofit2.awaitResponse
import java.io.IOException
import javax.inject.Inject

class GetCodeUseCase @Inject constructor(
    private val api_repository: IisApiRepository,
    private val db_repository: UserDataBaseRepository
) {
    operator fun invoke(id : Int, value : String): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            val cookie = db_repository.getCookie()
            if (cookie == null) {
                emit(Resource.Error<Int>("LessCookie"))
            }
            if (cookie != null) {
                Log.e("~~~", "\"Start\"")

                api_repository.updateEmail(cookie, EmailChangeDto(id, value)).enqueue(
                    object: Callback<Any?> {
                        override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                            Log.e("~~~","resp")
                        }

                        override fun onFailure(call: Call<Any?>, t: Throwable) {
                            Log.e("~~~","fail")
                        }

                    }
                )
                Log.e("~~~", "END")
                //val data = call.await()
                //Log.e("~~~", data.toString())
               /* if(data. == 200) {
                  api_repository.getCodeForEmail(cookie, id);   
                }*/
                emit(Resource.Success<Int>(0))
            }
        } catch (e: HttpException) {
            val loginAndPassword = db_repository.getLoginAndPassword()
            if (loginAndPassword?.login == null || loginAndPassword.password == null) {
                emit(Resource.Error<Int>("LessCookie"))
            } else {
                try {
                    val responseFromLogin =
                        api_repository.loginToAccount(
                            loginAndPassword.login.toString(),
                            loginAndPassword.password.toString()
                        )
                            .awaitResponse()
                    val cookie = responseFromLogin.headers()["Set-Cookie"].toString()
                    db_repository.setCookie(cookie)
                    Log.e("~~~!", "Start")
                    val data = api_repository.updateEmail(cookie, EmailChangeDto(id, value))
                    Log.e("~~~!", data.toString())
                   /* if(data.code() == 200) {
                        api_repository.getCodeForEmail(cookie, id);
                    }*/
                    //emit(Resource.Success<Int>(data.code()))
                } catch (e: HttpException) {
                    emit(
                        Resource.Error<Int>(e.localizedMessage ?: "ConnectionError")
                    )
                } catch (e: IOException) {
                    emit(
                        Resource.Error<Int>(
                            "LessCookie"
                        )
                    )
                }
            }
        } catch (_: IOException) {

        }
    }
}