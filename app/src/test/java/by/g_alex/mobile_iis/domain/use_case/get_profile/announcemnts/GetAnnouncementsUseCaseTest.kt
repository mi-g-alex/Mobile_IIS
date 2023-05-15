package by.g_alex.mobile_iis.domain.use_case.get_profile.announcemnts

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.announcemnt.AnnouncemntDto
import by.g_alex.mobile_iis.data.remote.dto.departments.EmployeeDetailInfoDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.departments_list.GetEmployeeDetailsInfoUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetAnnouncementsUseCaseTest {
    val api_rep = mock<IisApiRepository>()
    val db_rep = mock<UserDataBaseRepository>()


    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment =
            listOf( AnnouncemntDto(
                "",
                "",
                "",
                "",
                emptyList(),
                "",
                3,
                "",
                emptyList(),
                ""
                )
            )

        Mockito.`when`(api_rep.getAnnouncements("s")).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetAnnouncementsUseCase(api_rep,db_rep)
        val results = mutableListOf<Resource<List<AnnouncemntDto>>>()
        useCase.invoke().collect { value -> results.add(value) }



        Assertions.assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}