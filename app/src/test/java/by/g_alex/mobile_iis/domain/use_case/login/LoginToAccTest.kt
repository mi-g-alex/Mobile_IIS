package by.g_alex.mobile_iis.domain.use_case.login

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class RestorePasswordApplyUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val test = false
        Mockito.`when`(
            api_rep.restorePasswordApply(
                "25350033",
                "1234567",
                "saaasdf@sadfa.asdf",
                "34567"
            )
        ).thenReturn(null)

        val useCase = RestorePasswordApplyUseCase(api_rep)
        val results = mutableListOf<Resource<Boolean>>()
        useCase.invoke("25350033",
            "1234567",
            "saaasdf@sadfa.asdf",
            "34567").collect { value -> results.add(value) }



        Assertions.assertEquals(test, results[1].data)

    }
}