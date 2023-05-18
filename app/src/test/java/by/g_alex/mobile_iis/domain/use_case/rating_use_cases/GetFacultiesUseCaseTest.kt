package by.g_alex.mobile_iis.domain.use_case.rating_use_cases

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.faculties.FacultiesDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetFacultiesUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val test = listOf<FacultiesDto>(
            FacultiesDto(
                10, "12345"
            )
        )


        Mockito.`when`(api_rep.getFaculties()).thenReturn(test)

        val useCase = GetFacultiesUseCase(api_rep)
        val results = mutableListOf<Resource<List<FacultiesDto>>>()
        useCase.invoke().collect { value -> results.add(value) }



        Assertions.assertEquals(test, results[1].data)

    }
}