package by.g_alex.mobile_iis.presentation.schedule.additional_views

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.common.Constants.FAVOURITE_SCHEDULE
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    viewModel: ScheduleViewModel = hiltViewModel(),
    navController: NavController,
    scope: CoroutineScope,
    bottomSheetState: ModalBottomSheetState,
) {
    val groups = remember { mutableStateOf(viewModel.getGroups()) }
    val employees = remember { mutableStateOf(viewModel.getEmployees()) }
    Box(Modifier.background(MaterialTheme.colorScheme.background)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            items(groups.value) { item ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = item,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .weight(0.7f)
                            //.fillMaxWidth()
                            .clickable {
                                viewModel.getSchedule(item)
                                viewModel.headerText.value = item
                                Log.e("newHead", viewModel.headerText.value)
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                            }
                    )
                    Icon(
                        painter = if (viewModel.favourite.value == item) painterResource(id = R.drawable.star_icon)
                        else painterResource(id = R.drawable.star_o_icon),
                        contentDescription = "sdcscds",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                val preferences = viewModel.context.getSharedPreferences(
                                    FAVOURITE_SCHEDULE, Context.MODE_PRIVATE
                                )
                                preferences
                                    .edit()
                                    .putString(FAVOURITE_SCHEDULE, item)
                                    .apply()
                                viewModel.favourite.value = item
                            },
                        tint = if (viewModel.favourite.value == item) Color(0xffffbf00)
                        else MaterialTheme.colorScheme.inverseSurface
                    )
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.delete_icon),
                        contentDescription = "Delete",
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
                            .align(Alignment.CenterVertically)
                            .clickable {
                                if (viewModel.favourite.value == item) {
                                    val preferences = viewModel.context.getSharedPreferences(
                                        FAVOURITE_SCHEDULE, Context.MODE_PRIVATE
                                    )
                                    preferences
                                        .edit()
                                        .putString(FAVOURITE_SCHEDULE, "")
                                        .apply()
                                    viewModel.favourite.value = "Добавить"
                                }
                                viewModel.state.value.Days = null
                                viewModel.deleteScheduleFromDb(item)
                                groups.value = viewModel.getGroups()
                                Log.e("EFWWEFW", groups.value.toString())
                                if (groups.value.isNotEmpty()) {
                                    viewModel.headerText.value = groups.value.first()
                                    viewModel.getSchedule(groups.value.first())
                                } else {
                                    viewModel.headerText.value = "Добавить"
                                }

                            },
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)
                    )
                }
            }

            item {
                if (viewModel.getEmployees().isNotEmpty() && viewModel.getGroups().isNotEmpty())
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
            }

            items(viewModel.getEmployees()) { item ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = item,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(vertical = 5.dp)
                            .weight(0.7f)
                            .clickable {
                                viewModel.getEmployeeSchedule(item); viewModel.headerText.value =
                                item
                                scope.launch {
                                    bottomSheetState.hide()
                                }
                            })
                    Icon(
                        painter = if (viewModel.favourite.value == item) painterResource(id = R.drawable.star_icon)
                        else painterResource(id = R.drawable.star_o_icon),
                        contentDescription = "sdcscds",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .clickable {
                                val preferences = viewModel.context.getSharedPreferences(
                                    FAVOURITE_SCHEDULE, Context.MODE_PRIVATE
                                )
                                preferences
                                    .edit()
                                    .putString(FAVOURITE_SCHEDULE, item)
                                    .apply()
                                viewModel.favourite.value = item
                            },
                        tint = if (viewModel.favourite.value == item) Color.Yellow
                        else MaterialTheme.colorScheme.inverseSurface
                    )
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.delete_icon),
                        contentDescription = "Delete",
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(top = 5.dp, bottom = 5.dp, end = 5.dp)
                            .align(Alignment.CenterVertically)
                            .clickable {
                                viewModel.deleteEmployeeScheduleFromDb(item)
                                employees.value = viewModel.getEmployees()
                                if (employees.value.isNotEmpty()) {
                                    viewModel.headerText.value = employees.value.first()
                                    viewModel.getSchedule(employees.value.first())
                                } else {
                                    viewModel.headerText.value = "Добавить"
                                }
                            },
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)
                    )
                }
            }

            item {
                if (viewModel.getEmployees().isNotEmpty() && viewModel.getGroups().isNotEmpty())
                    Divider(
                        modifier = Modifier
                            .padding(5.dp)
                            .height(1.dp)
                            .fillMaxWidth(), color = MaterialTheme.colorScheme.outline
                    )
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
                        imageVector = ImageVector.vectorResource(R.drawable.add_icon),
                        contentDescription = "Add",
                        modifier = Modifier
                            .height(70.dp)
                            .width(70.dp)
                            .padding(top = 5.dp, bottom = 5.dp, end = 5.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.inverseSurface)
                    )
                    Text(
                        text = "Добавить",
                        fontSize = 30.sp,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}