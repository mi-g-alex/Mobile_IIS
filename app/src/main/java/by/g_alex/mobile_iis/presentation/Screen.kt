package by.g_alex.mobile_iis.presentation

sealed class Screen (val route : String){
    object LoginScreen : Screen("login_screen")
    object ProfileScreen : Screen("profile_screen")
}