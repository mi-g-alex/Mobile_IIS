package by.g_alex.mobile_iis.domain.use_case.schedule_use_cases

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.students.StudentResponceDto
import by.g_alex.mobile_iis.data.remote.dto.students.StudentsRequestDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.use_case.students_use_case.GetStudentProfileUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCurrentWeekUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment = 1

        Mockito.`when`(api_rep.getCurrentWeek()).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetCurrentWeekUseCase(api_rep)
        val results = mutableListOf<Resource<Int>>()
        useCase.invoke().collect { value -> results.add(value) }



        Assertions.assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}