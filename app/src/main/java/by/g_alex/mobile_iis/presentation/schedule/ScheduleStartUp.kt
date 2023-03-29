package by.g_alex.mobile_iis.presentation.schedule


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import by.g_alex.mobile_iis.common.Constants.ADDED_GROUPS
import by.g_alex.mobile_iis.common.Constants.ADDED_SCHEDULE
import by.g_alex.mobile_iis.presentation.schedule.navigation.SetUpNavGraph


@Composable
fun ScheduleStartUp(
    viewModel: ScheduleViewModel = hiltViewModel(),
) {

    viewModel.context = LocalContext.current

    val preferences = remember {
        mutableStateOf(
            viewModel.context.getSharedPreferences(
                ADDED_GROUPS,
                Context.MODE_PRIVATE
            )
        )
    }

    viewModel.getCurrentWeek()

    val set = preferences.value.getStringSet(ADDED_GROUPS, emptySet())

    if (set?.isNotEmpty() == true) {
        viewModel.getSchedule(set.minOf { it })
        viewModel.headerText.value = set.minOf { it }
    } else {
        preferences.value =
            viewModel.context.getSharedPreferences(ADDED_SCHEDULE, Context.MODE_PRIVATE)
        val prepSet = preferences.value.getStringSet(ADDED_SCHEDULE, emptySet())
        if (prepSet?.isNotEmpty() == true) {
            viewModel.getEmployeeSchedule(
                preferences.value.getString(prepSet.minOf { it }, "") ?: ""
            )
            viewModel.headerText.value = prepSet.first()
        }
    }
    val navController: NavHostController = rememberNavController()
    SetUpNavGraph(navController = navController, viewModel = viewModel)
}

