package by.g_alex.mobile_iis.data.remote.dto.study


data class StudyMarkSheetDto(
    val absentDate: Any?, // null
    val certificate: Boolean?, // false
    val createDate: String?, // 09.02.2023
    val employee: Employee?,
    val expireDate: Any?, // null
    val hours: Any?, // null
    val id: Int?, // 69949
    val markSheetType: MarkSheetType?,
    val number: String?, // 405/0817
    val paymentFormMap: Any?, // null
    val price: Double?, // 2.52
    val reason: Int?, // 2
    val rejectionReason: Any?, // null
    val requestValidationDoc: Any?, // null
    val retakeCount: Int?, // 1
    val status: String?, // ��������������������
    val subject: Subject?,
    val term: Int? // 1
) {
    data class Employee(
        val academicDepartment: String?, // ������.������
        val fio: String?, // ���������� ��. ��.
        val firstName: String?, // ��������
        val id: Int?, // 500550
        val lastName: String?, // ����������
        val middleName: String?, // ��������������������������
        val price: Double? // 6.3
    )

    data class MarkSheetType(
        val coefficient: Double?, // 0.4
        val fullName: String?, // 1231231
        val id: Int?, // 1
        val isCourseWork: Boolean?, // false
        val isExam: Boolean?, // true
        val isLab: Boolean?, // false
        val isOffset: Boolean?, // false
        val isRemote: Boolean?, // false
        val price: Any?, // null
        val shortName: String? // ������., ������. ���������� (����., ������., ��������.)
    )

    data class Subject(
        val abbrev: String?, // ������������
        val focsId: Int?, // 204842
        val id: Int?, // 23215
        val lessonTypeAbbrev: String?, // ����������.
        val name: String?, // ���������������������������� �� �������������������� ������������ ���������������� ������������������
        val term: Int?, // 1
        val thId: Any? // null
    )
}
