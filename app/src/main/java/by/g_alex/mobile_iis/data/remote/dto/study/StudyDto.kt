package by.g_alex.mobile_iis.data.remote.dto.study

data class StudyDto(
    val applications: List<StudyApplicationsDto>?,
    val certifications: List<StudyCertificationsDto>?,
    val mark_sheet: List<StudyMarkSheetDto>?,
    val lib_debts: List<String>?
)
