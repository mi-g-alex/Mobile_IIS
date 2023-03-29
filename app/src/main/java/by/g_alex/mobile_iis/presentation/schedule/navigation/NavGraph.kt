package by.g_alex.mobile_iis.presentation.schedule.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import by.g_alex.mobile_iis.presentation.schedule.main_screens.AddNewScreen
import by.g_alex.mobile_iis.presentation.schedule.main_screens.ScheduleListScreen
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.navigation.Screen

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    viewModel: ScheduleViewModel
) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            ScheduleListScreen(viewModel = viewModel, navController = navController)
        }
        composable(Screen.Search.route) {
            AddNewScreen(viewModel,navController)
        }
    }
}