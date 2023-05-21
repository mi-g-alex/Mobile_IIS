package by.g_alex.mobile_iis.domain.use_case.get_diciplines_use_case

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.diciplines.DiciplinesDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetDisciplinesUseCaseTest {

    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val test = listOf<DiciplinesDto>(
            DiciplinesDto(
                10, "Информатика"
            )
        )
        Mockito.`when`(api_rep.getDisciplines(10, 2022)).thenReturn(test)

        val useCase = GetDisciplinesUseCase(api_rep)
        val results = mutableListOf<Resource<List<DiciplinesDto>>>()
        useCase.invoke(10, 2022).collect { value -> results.add(value) }

        assertEquals(test, results[1].data)

    }
}