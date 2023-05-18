package by.g_alex.mobile_iis.domain.use_case.departments_list

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetDepartmentsNameUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val test = "sdcd"

        Mockito.`when`(api_rep.getDepartmentName(23)).thenReturn(test)

        val useCase = GetDepartmentsNameUseCase(api_rep)
        val results = mutableListOf<Resource<String>>()
        useCase.invoke(23).collect { value -> results.add(value) }



        Assertions.assertEquals(test, results[1].data)

    }
}