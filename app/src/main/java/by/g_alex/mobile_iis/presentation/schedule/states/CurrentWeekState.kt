package by.g_alex.mobile_iis.presentation.schedule.states

data class CurrentWeekState(
    val isLoading: Boolean = false,
    var week : Int? = 0,
    val error:String = ""
)
