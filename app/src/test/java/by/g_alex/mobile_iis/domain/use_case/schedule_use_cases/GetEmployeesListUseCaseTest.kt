package by.g_alex.mobile_iis.domain.use_case.schedule_use_cases

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.data.remote.dto.schedule.EmployeeDto
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetEmployeesListUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment = listOf(
           EmployeeModel(
                emptyList(),
                "sd",
                "sd",
                "",
                12,
                "",
                "",
                "",
                "",
               ""
            )
        )

        Mockito.`when`(api_rep.getEmployees()).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetEmployeesListUseCase(api_rep)
        val results = mutableListOf<Resource<List<EmployeeModel>>>()
        useCase.invoke().collect { value -> results.add(value) }



        Assertions.assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}