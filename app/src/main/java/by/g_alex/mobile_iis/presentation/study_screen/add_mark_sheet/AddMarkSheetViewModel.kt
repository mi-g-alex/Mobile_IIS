package by.g_alex.mobile_iis.presentation.study_screen.add_mark_sheet

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetFocusThIdDto
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetSubjectsDto
import by.g_alex.mobile_iis.data.remote.dto.study.mark_sheet.MarkSheetTypesDto
import by.g_alex.mobile_iis.domain.use_case.get_profile.study.mark_sheet.FindEmployeeByFioUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.study.mark_sheet.GeMarkSheetByIdUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.study.mark_sheet.GeMarkSheetSubjectsUseCase
import by.g_alex.mobile_iis.domain.use_case.get_profile.study.mark_sheet.GetMarkSheetTypesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddMarkSheetViewModel @Inject constructor(
    private val getMarkSheetTypesUseCase: GetMarkSheetTypesUseCase,
    private val getMarkSheetSubjectsUseCase: GeMarkSheetSubjectsUseCase,
    private val findEmployeeByFioUseCase: FindEmployeeByFioUseCase,
    private val getMarkSheetByIdUseCase: GeMarkSheetByIdUseCase
) : ViewModel() {


    val isLoading = mutableStateOf(false)
    val isError = mutableStateOf<String?>(null)
    var typesOfMarkSheet = mutableStateOf(listOf<MarkSheetTypesDto?>())
    var subjOfMarkSheet = mutableStateOf(listOf<MarkSheetSubjectsDto?>())
    var employeeList = mutableStateOf(listOf<MarkSheetFocusThIdDto?>())
    var employeeListById = mutableStateOf(listOf<MarkSheetFocusThIdDto?>())

    val expandedSubj = mutableStateOf(false)
    val selectedSubj = mutableStateOf<MarkSheetSubjectsDto?>(null)
    val selectedTextSubj = mutableStateOf("")

    val expandedTypeOfSubj = mutableStateOf(false)
    val selectedTypeOfSubj = mutableStateOf<MarkSheetSubjectsDto.LessonType?>(null)
    val selectedTextTypeOfSubj = mutableStateOf("")


    val expandedEmployeeListById = mutableStateOf(false)
    val selectedEmployeeListById = mutableStateOf<MarkSheetFocusThIdDto?>(null)
    val selectedTextEmployeeListById = mutableStateOf("")

    val isGoodReason = mutableStateOf(false)

    val studentIdText = mutableStateOf("")


    init {
        getAll()
    }

    private fun getAll() {
        getMarkSheetTypesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    typesOfMarkSheet.value = result.data!!
                    isLoading.value = false
                }

                is Resource.Loading -> {
                    isLoading.value = true
                }

                is Resource.Error -> {
                    isLoading.value = false
                    isError.value = result.message.toString()
                }
            }
        }.launchIn(viewModelScope)

        getMarkSheetSubjectsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    subjOfMarkSheet.value = result.data!!
                    isLoading.value = false
                }

                is Resource.Loading -> {
                    isLoading.value = true
                }

                is Resource.Error -> {
                    isLoading.value = false
                    isError.value = result.message.toString()
                }
            }
        }.launchIn(viewModelScope)

        findEmployeeByFioUseCase("").onEach { result ->
            when (result) {
                is Resource.Success -> {
                    for (i in result.data!!) {
                        employeeList.value += i
                    }
                }

                is Resource.Loading -> {
                }

                is Resource.Error -> {
                    isError.value = result.message.toString()
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getListById() {
        if (selectedTypeOfSubj.value != null)
            getMarkSheetByIdUseCase(
                selectedTypeOfSubj.value!!.focsId,
                selectedTypeOfSubj.value!!.thId
            ).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        employeeListById.value = result.data!!
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                        isError.value = result.message.toString()
                    }
                }
            }.launchIn(viewModelScope)
    }
}