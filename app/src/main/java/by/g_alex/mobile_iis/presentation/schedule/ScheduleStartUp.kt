package by.g_alex.mobile_iis.presentation.schedule


import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import by.g_alex.mobile_iis.common.Constants.ADDED_GROUPS
import by.g_alex.mobile_iis.common.Constants.ADDED_SCHEDULE
import by.g_alex.mobile_iis.common.Constants.FAVOURITE_SCHEDULE
import by.g_alex.mobile_iis.presentation.schedule.navigation.SetUpNavGraph


@Composable
fun ScheduleStartUp(
    viewModel: ScheduleViewModel = hiltViewModel(),
) {

   // viewModel.context = LocalContext.current
    Log.e("USE","USAGE")
    val preferences =
            viewModel.context.getSharedPreferences(
                ADDED_GROUPS,
                Context.MODE_PRIVATE
            )

   // viewModel.getCurrentWeek()

    val set = preferences.getStringSet(ADDED_GROUPS, emptySet())

    val favouritePrefs =
            viewModel.context.getSharedPreferences(
                FAVOURITE_SCHEDULE,
                Context.MODE_PRIVATE
            )


    val specialSched = favouritePrefs.getString(FAVOURITE_SCHEDULE,"")
    if((specialSched == null || specialSched == "" || specialSched == "Добавить")&&viewModel.headerText.value=="Добавить") {
        if (set?.isNotEmpty() == true) {
            viewModel.getSchedule(set.minOf { it })
            viewModel.headerText.value = set.minOf { it }
            Log.e("HEADER",viewModel.headerText.value)
        } else {
            val prefer =
                viewModel.context.getSharedPreferences(ADDED_SCHEDULE, Context.MODE_PRIVATE)
            val prepSet = prefer.getStringSet(ADDED_SCHEDULE, emptySet())
            if (prepSet?.isNotEmpty() == true) {
                viewModel.getEmployeeSchedule(
                    prepSet.first()
                )
                viewModel.headerText.value = prepSet.first()
            }
        }
    }
    else if(viewModel.headerText.value=="Добавить" && specialSched!="" &&specialSched != "Добавить"){
        if(specialSched!!.contains("."))
            viewModel.getEmployeeSchedule(specialSched)
        else viewModel.getSchedule(specialSched)
        viewModel.headerText.value = specialSched
        viewModel.favourite.value = specialSched
    }
    val navController: NavHostController = rememberNavController()
    SetUpNavGraph(navController = navController)
}

