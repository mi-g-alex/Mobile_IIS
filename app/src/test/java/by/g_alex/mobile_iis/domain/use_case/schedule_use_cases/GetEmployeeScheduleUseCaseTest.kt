package by.g_alex.mobile_iis.domain.use_case.schedule_use_cases

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetEmployeeScheduleUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment = listOf(
            LessonModel(
                0,
                "sd",
                emptyList(),
                "sd",
                "",
                23,
                "",
                "",
                "",
                emptyList(),
                "",
                "",
                "",
                false,
                "",
                ""
            )
        )

        Mockito.`when`(api_rep.getSchedule("sc")).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetScheduleUseCase(api_rep)
        val results = mutableListOf<Resource<List<LessonModel>>>()
        useCase.invoke("sc").collect { value -> results.add(value) }



        Assertions.assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}