package by.g_alex.mobile_iis.presentation.schedule


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import by.g_alex.mobile_iis.common.Constants.ADDEDGROUPS
import by.g_alex.mobile_iis.common.Constants.ADDEDPREPS
import com.example.compose.presentation.list.SetUpNavGraph


@Composable
fun ScheduleStartUp() {

    val viewModel = ScheduleViewModel()
    viewModel.context = LocalContext.current

    val preferences = remember{mutableStateOf(viewModel.context.getSharedPreferences(ADDEDGROUPS, Context.MODE_PRIVATE))}

    viewModel.getCurrentWeek()

    val set = preferences.value.getStringSet(ADDEDGROUPS, emptySet())
    if (set?.isNotEmpty() == true) {
        viewModel.getScheadule(set.minOf { it })
        viewModel.headertext.value = set.minOf { it }
    } else {
        preferences.value = viewModel.context.getSharedPreferences(ADDEDPREPS, Context.MODE_PRIVATE)
        val prepSet = preferences.value.getStringSet(ADDEDPREPS, emptySet())
        if (prepSet?.isNotEmpty() == true) {
            viewModel.getPrepodScheadule(
                preferences.value.getString(prepSet.minOf { it }, "") ?: ""
            )
            viewModel.headertext.value = prepSet.first()
        }
    }
    val navController: NavHostController = rememberNavController()
    SetUpNavGraph(navController = navController, viewModel = viewModel)


}

