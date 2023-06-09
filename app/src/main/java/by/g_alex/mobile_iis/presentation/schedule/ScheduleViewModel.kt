package by.g_alex.mobile_iis.presentation.schedule

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.mobile_iis.common.Constants.ADDED_GROUPS
import by.g_alex.mobile_iis.common.Constants.ADDED_SCHEDULE
import by.g_alex.mobile_iis.common.Constants.CURRENT_WEEK
import by.g_alex.mobile_iis.common.Resource
import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.domain.model.profile.schedule.EmployeeModel
import by.g_alex.mobile_iis.domain.repository.UserDataBaseRepository
import by.g_alex.mobile_iis.domain.use_case.schedule_use_cases.GetCurrentWeekUseCase
import by.g_alex.mobile_iis.domain.use_case.schedule_use_cases.GetEmployeeScheduleUseCase
import by.g_alex.mobile_iis.domain.use_case.schedule_use_cases.GetEmployeesListUseCase
import by.g_alex.mobile_iis.domain.use_case.schedule_use_cases.GetExamsUseCase
import by.g_alex.mobile_iis.domain.use_case.schedule_use_cases.GetGroupsUseCase
import by.g_alex.mobile_iis.domain.use_case.schedule_use_cases.GetScheduleUseCase
import by.g_alex.mobile_iis.presentation.schedule.states.CurrentWeekState
import by.g_alex.mobile_iis.presentation.schedule.states.EmployeeState
import by.g_alex.mobile_iis.presentation.schedule.states.ExamState
import by.g_alex.mobile_iis.presentation.schedule.states.GroupState
import by.g_alex.mobile_iis.presentation.schedule.states.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val getScheduleUseCase: GetScheduleUseCase,
    private val getCurrentWeekUseCase: GetCurrentWeekUseCase,
    private val getEmployeeScheduleUseCase: GetEmployeeScheduleUseCase,
    private val getGroupsUseCase: GetGroupsUseCase,
    private val getEmployeesListUseCase: GetEmployeesListUseCase,
    private val getExamsUseCase: GetExamsUseCase,
    private val db: UserDataBaseRepository
) : ViewModel() {

    private val _state = mutableStateOf(ScheduleState())
    private val _eState = mutableStateOf(ExamState())
    private val _wState = mutableStateOf(CurrentWeekState())
    private val _grState = mutableStateOf(GroupState())
    private val _prState = mutableStateOf(EmployeeState())

    val headerText = mutableStateOf("Добавить")
    val favourite = mutableStateOf("Добавить")
    val state: State<ScheduleState> = _state
    val exState: State<ExamState> = _eState
    val weekState: State<CurrentWeekState> = _wState
    val groupState: State<GroupState> = _grState
    val prepState: State<EmployeeState> = _prState

    init {
        getCurrentWeek()
    }

    fun addEmployees(employee: EmployeeModel) {
        val prefs = context.getSharedPreferences(ADDED_SCHEDULE, Context.MODE_PRIVATE)
        val list = prefs.getStringSet(ADDED_SCHEDULE, emptySet())?.toMutableSet()
        list?.add(employee.fio)
        prefs.edit().putStringSet(ADDED_SCHEDULE, list).apply()
        prefs.edit().putString(employee.fio, employee.urlId).apply()
    }

    fun getEmployees(): List<String> {
        val prefs = context.getSharedPreferences(ADDED_SCHEDULE, Context.MODE_PRIVATE)
        val list = prefs.getStringSet(ADDED_SCHEDULE, emptySet())
        return list?.toList()?.sorted() ?: emptyList()
    }

    fun addGroups(group: String) {
        val prefs = context.getSharedPreferences(ADDED_GROUPS, Context.MODE_PRIVATE)
        val list = prefs.getStringSet(ADDED_GROUPS, emptySet())?.toMutableSet()
        list?.add(group)
        prefs.edit().putStringSet(ADDED_GROUPS, list).apply()
    }

    fun deleteEmployeeScheduleFromDb(name: String) {
        val prefs = context.getSharedPreferences(ADDED_SCHEDULE, Context.MODE_PRIVATE)
        val list = prefs.getStringSet(ADDED_SCHEDULE, emptySet())?.toMutableSet()
        list?.remove(name)
        prefs.edit().remove(ADDED_SCHEDULE).putStringSet(ADDED_SCHEDULE, list).apply()

        viewModelScope.launch { db.deleteSchedulebyName(name) }
    }

    fun deleteScheduleFromDb(name: String) {
        val prefs = context.getSharedPreferences(ADDED_GROUPS, Context.MODE_PRIVATE)
        val list = prefs.getStringSet(ADDED_GROUPS, emptySet())?.toMutableSet()
        list?.remove(name)
        prefs.edit().remove(ADDED_GROUPS).putStringSet(ADDED_GROUPS, list).apply()

        viewModelScope.launch { db.deleteSchedulebyName(name) }
    }

    fun getGroups(): List<String> {
        val prefs = context.getSharedPreferences(ADDED_GROUPS, Context.MODE_PRIVATE)
        val list = prefs.getStringSet(ADDED_GROUPS, emptySet())
        return list?.toList()?.sorted() ?: emptyList()
    }

    fun getEmployee() {
        getEmployeesListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _prState.value = EmployeeState(preps = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _prState.value = EmployeeState(error = result.message ?: "Zalupa")
                }

                is Resource.Loading -> {
                    _prState.value = EmployeeState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getGroup() {
        getGroupsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _grState.value = GroupState(Groups = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    _grState.value = GroupState(error = result.message ?: "Zalupa")
                }

                is Resource.Loading -> {
                    _grState.value = GroupState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getExams(grNum: String) {
        if (grNum == "Добавить")
            return
        viewModelScope.launch {
            val consult: MutableList<LessonModel> =
                if(grNum.isDigitsOnly()) db.getScheduleByAbbv("Консультация", grNum).toMutableList()
                else db.getExEmplScheduleByAbbv("Консультация", grNum).toMutableList()
            val daoExams: MutableList<LessonModel> =
                if(grNum.isDigitsOnly()) db.getScheduleByAbbv("Экзамен", grNum).toMutableList()
                else db.getExEmplScheduleByAbbv("Экзамен", grNum).toMutableList()
            consult.addAll(daoExams)
            consult.sortBy { it.dateEnd }
            if (consult.isNotEmpty())
                _eState.value = ExamState(exams = consult)
        }
        getExamsUseCase(grNum).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _eState.value = ExamState(exams = result.data)
                    db.deleteSchedulebyAbbv("Консультация", grNum)
                    db.deleteSchedulebyAbbv("Экзамен", grNum)
                    for (n in _eState.value.exams ?: emptyList()) {
                        if(!grNum.isDigitsOnly())
                            n.note = grNum
                        db.insertSchedule(n)
                    }
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {
                    _eState.value = ExamState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getSchedule(grNum: String) {

        if (grNum == "Добавить")
            return
        viewModelScope.launch {
            val schedules: List<LessonModel> = db.getSchedule(grNum)
            Log.e("schedules",schedules.toString())
            if (schedules.isNotEmpty())
                _state.value = ScheduleState(Days = schedules)
        }
        getScheduleUseCase(grNum).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val stop = mutableStateOf(false)
                    _state.value = ScheduleState(Days = result.data)
                    db.deleteSchedulebyName(grNum)
                    for (n in _state.value.Days ?: emptyList()) {
                        db.insertSchedule(n)
                    }
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {
                    _state.value = ScheduleState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }


    fun getEmployeeSchedule(fio: String) {
        val prefs =
            context.getSharedPreferences(ADDED_SCHEDULE, Context.MODE_PRIVATE)
        val urlId = prefs.getString(fio, fio) ?: fio
        Log.e("FIO",fio)
        viewModelScope.launch {
            val schedules: List<LessonModel> = db.getEmployeeSchedule(urlId)
            if (schedules.isNotEmpty())
                _state.value = ScheduleState(Days = schedules)

        }
        getEmployeeScheduleUseCase(urlId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ScheduleState(Days = result.data)
                    db.deleteEmployeeSchedule(urlId)
                        for (n in _state.value.Days ?: emptyList()) {
                            n.fio = urlId;
                            db.insertSchedule(n)
                        }
                }

                is Resource.Error -> {
                }

                is Resource.Loading -> {
                    _state.value = ScheduleState(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }

    fun getCurrentWeek() {
        val prefs = context.getSharedPreferences(CURRENT_WEEK, Context.MODE_PRIVATE)


        getCurrentWeekUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _wState.value = CurrentWeekState(week = result.data)
                    prefs.edit().putInt(CURRENT_WEEK, _wState.value.week ?: 0).apply()
                    Log.e("WEEEEK", _wState.value.week.toString())
                }

                is Resource.Error -> {

                    Log.e("WEEEEKer", _wState.value.week.toString())
                }

                is Resource.Loading -> {
                    _wState.value = CurrentWeekState(isLoading = true)
                    val wek = prefs.getInt(CURRENT_WEEK, 1)

                    _wState.value = CurrentWeekState(week = wek)

                }
            }

        }.launchIn(viewModelScope)
    }

}