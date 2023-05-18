package by.g_alex.mobile_iis.domain.use_case.phone_book

import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.phone_book.PhoneSearchDto
import by.g_alex.mobile_iis.data.remote.dto.phone_book.RequestDto
import by.g_alex.mobile_iis.domain.repository.IisApiRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetPhoneBookUseCaseTest {
    val api_rep = mock<IisApiRepository>()

    @Test
    fun test1() = runBlocking {
        val testAnnouncementsByDepartment =
            PhoneSearchDto(
               emptyList(),
                1,

            )

        Mockito.`when`(api_rep.getPhone(RequestDto(1,1,""))).thenReturn(testAnnouncementsByDepartment)

        val useCase = GetPhoneBookUseCase(api_rep)
        val results = mutableListOf<Resource<PhoneSearchDto>>()
        useCase.invoke(RequestDto(1,1,"")).collect { value -> results.add(value) }



        Assertions.assertEquals(testAnnouncementsByDepartment, results[1].data)

    }
}