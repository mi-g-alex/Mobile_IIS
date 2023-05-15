package by.g_alex.mobile_iis.domain.use_case.departments_list

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentsTreeDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetDepartmentsTreeUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment = listOf(
            DepartmentsTreeDto(
                emptyList(),
                null
            )
        )

        Mockito.`when`(api_rep.getDepartmentsTree()).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetDepartmentsTreeUseCase(api_rep)
        val results = mutableListOf<Resource<List<DepartmentsTreeDto>>>()
        useCase.invoke().collect { value -> results.add(value) }



        Assertions.assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}