package by.g_alex.mobile_iis.domain.use_case.department_schedule_use_case

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.StudentGroup
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock

class GetAnnouncementsByDepartmentTest {

    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment = listOf<AnnouncemntDto>(
            AnnouncemntDto(
                auditory = "200",
                content = "200",
                date = "21.12.2004",
                employee = "MyName",
                employeeDepartments = listOf("Информатрика"),
                endTime = "15:20",
                id = 1,
                startTime = "14:00",
                studentGroups = listOf(
                    StudentGroup(
                        id = 1,
                        name = "12345",
                        specialityAbbrev = "123456"
                    )
                ),
                urlId = "234"
            )
        )
        Mockito.`when`(api_rep.getDepartmentAnons(1)).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetAnnouncementsByDepartment(api_rep)
        val results = mutableListOf<Resource<List<AnnouncemntDto>>>()
        useCase.invoke(1).collect { value -> results.add(value) }



       assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}