package by.g_alex.mobile_iis.domain.use_case.departments_list

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.departments.DepartmentEmployeesDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetDepartmentsEmployeesUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment = listOf(
            DepartmentEmployeesDto(
                "",
                "sd",
                "sd",
                "",
                "",
                "",
                23,
                emptyList(),
                "",
                "",
                "",
                "",
                ""
                )
        )

        Mockito.`when`(api_rep.getDepartmentEmployees(1)).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetDepartmentsEmployeesUseCase(api_rep)
        val results = mutableListOf<Resource<List<DepartmentEmployeesDto>>>()
        useCase.invoke(1).collect { value -> results.add(value) }



        Assertions.assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}