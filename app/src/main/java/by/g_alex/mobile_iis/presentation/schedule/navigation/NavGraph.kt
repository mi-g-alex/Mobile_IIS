package by.g_alex.mobile_iis.presentation.schedule.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.main_screens.AddNewScreen
import by.g_alex.mobile_iis.presentation.schedule.main_screens.ScheduleListScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            ScheduleListScreen( navController = navController)
        }
        composable(Screen.Search.route) {
            AddNewScreen(viewModel,navController)
        }
    }
}