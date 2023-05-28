package by.g_alex.mobile_iis.domain.use_case.students_use_case

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.students.StudentResponseDto
import by.g_alex.mobile_iis.data.remote.dto.students.StudentsRequestDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetStudentProfileUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment =
            StudentResponseDto(
                course = listOf(1),
                currentPage = 1,
                cvs = emptyList(),
                faculties = emptyList(),
                lastName = "safes",
                pageSize = 12,
                searchJob = false,
                skills = emptyList(),
                totalItems = 12,
                totalPages = 12
            )

        Mockito.`when`(api_rep.getStudentProfiles(StudentsRequestDto(emptyList(),1, emptyList(),"",false,emptyList()))).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetStudentProfileUseCase(api_rep)
        val results = mutableListOf<Resource<StudentResponseDto>>()
        useCase.invoke(StudentsRequestDto(emptyList(),1, emptyList(),"",false,emptyList())).collect { value -> results.add(value) }



        Assertions.assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}