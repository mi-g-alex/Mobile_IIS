package by.g_alex.mobile_iis.domain.use_case.schedule_use_cases

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.GroupModel
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetGroupsUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment = listOf(
            GroupModel(
                "",
                23,
                2,
                "",
                12,
                23,
                "",
                "",
                3,
                ""
            )
        )

        Mockito.`when`(api_rep.getGroups()).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetGroupsUseCase(api_rep)
        val results = mutableListOf<Resource<List<GroupModel>>>()
        useCase.invoke().collect { value -> results.add(value) }



        Assertions.assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}