package by.g_alex.mobile_iis.domain.use_case.department_schedule_use_case

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.department.DepartmentDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetDepartmentsUseCaseTest {

    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val test = listOf<DepartmentDto>(
            DepartmentDto(
                "11", 1, "2123"
            )
        )
        Mockito.`when`(api_rep.getDepartments()).thenReturn(test)

        val useCase = GetDepartmentsUseCase(api_rep)
        val results = mutableListOf<Resource<List<DepartmentDto>>>()
        useCase.invoke().collect { value -> results.add(value) }

        assertEquals(test, results[1].data)

    }
}