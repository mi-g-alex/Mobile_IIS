package by.g_alex.mobile_iis.presentation.schedule.additional_views

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.common.Constants.ADDED_SCHEDULE
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    viewModel: ScheduleViewModel,
    navController: NavController,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
) {
    Box(Modifier.background(MaterialTheme.colorScheme.background)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            items(viewModel.getGroups()) { item ->
                Text(
                    text = item,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.getSchedule(item); viewModel.headerText.value =
                            item
                            scope.launch {
                                bottomSheetState.hide()
                            }
                        })

            }

            item {
                if (viewModel.getEmployees().isNotEmpty() && viewModel.getGroups().isNotEmpty())
                    Divider(Modifier.fillMaxWidth())
            }

            items(viewModel.getEmployees()) { item ->
                val prefs =
                    viewModel.context.getSharedPreferences(ADDED_SCHEDULE, Context.MODE_PRIVATE)
                val urlID = prefs.getString(item, "") ?: ""
                Text(
                    text = item,
                    fontSize = 30.sp,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .fillMaxWidth()
                        .clickable {
                            viewModel.getEmployeeSchedule(urlID); viewModel.headerText.value = item
                            scope.launch {
                                bottomSheetState.hide()
                            }
                        })

            }

            item {
                if (viewModel.getEmployees().isNotEmpty() && viewModel.getGroups().isNotEmpty())
                    Divider(Modifier.fillMaxWidth())
            }

            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(route = Screen.Search.route)
                        scope.launch {
                            bottomSheetState.hide()
                        }
                    }) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.plus),
                        contentDescription = "Add",
                        modifier = Modifier
                            .height(70.dp)
                            .width(70.dp)
                            .padding(top = 5.dp, bottom = 5.dp, end = 5.dp),
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                    Text(
                        text = "Add new",
                        fontSize = 30.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}