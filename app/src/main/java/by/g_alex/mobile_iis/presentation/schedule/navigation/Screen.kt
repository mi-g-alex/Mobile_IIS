package by.g_alex.mobile_iis.presentation.schedule.navigation

sealed class Screen(val route:String){
    object Home: Screen("Schedule")
    object Search: Screen("Group")

    object Exsam : Screen("Exams")
}
