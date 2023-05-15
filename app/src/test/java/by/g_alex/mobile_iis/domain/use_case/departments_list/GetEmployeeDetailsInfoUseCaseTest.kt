package by.g_alex.mobile_iis.domain.use_case.departments_list

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.departments.EmployeeDetailInfoDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetEmployeeDetailsInfoUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment =
            EmployeeDetailInfoDto(
            emptyList(),
            "sd",
            "sd",
            "",
            "",
            "",
            "",
            12,
            emptyList(),
            "",
                "",
                "",
                emptyList(),
                null,
                emptyList(),
                ""
        )

        Mockito.`when`(api_rep.getEmployeeDetailsInfo("s")).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetEmployeeDetailsInfoUseCase(api_rep)
        val results = mutableListOf<Resource<EmployeeDetailInfoDto>>()
        useCase.invoke("s").collect { value -> results.add(value) }



        Assertions.assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}