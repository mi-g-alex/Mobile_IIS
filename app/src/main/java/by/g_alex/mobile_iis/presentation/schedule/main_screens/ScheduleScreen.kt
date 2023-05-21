package by.g_alex.mobile_iis.presentation.schedule.main_screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.common.Constants
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.additional_views.BottomSheet
import by.g_alex.mobile_iis.presentation.schedule.additional_views.ScheduleColumn
import kotlinx.coroutines.launch


@Composable
fun ScheduleListScreen(
    viewModel: ScheduleViewModel = hiltViewModel(),
    navController: NavController
) {
//    val prefer =
//        viewModel.context.getSharedPreferences(Constants.ADDED_SCHEDULE, Context.MODE_PRIVATE)
//    val prepSet = prefer.getString(viewModel.headerText.value, "")
//    if (prepSet?.isNotEmpty() == true) {
//        viewModel.getEmployeeSchedule(
//            prepSet
//        )
//    }
//    if(viewModel.headerText.value.contains("."))viewModel.getSchedule(viewModel.headerText.value)
//    else {
//        viewModel.getEmployeeSchedule(viewModel.headerText.value)
//    }
    BottomSheetScaffold(viewModel, navController)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScaffold(
    viewModel: ScheduleViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    val exams = remember {
        viewModel.exState
    }
    val currentGroup = viewModel.headerText
    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            BottomSheet(

                navController = navController,
                scope = scope,
                bottomSheetState = bottomSheetState
            )
        },
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        sheetElevation = 12.dp
    ) {
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = currentGroup.value,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (exams.value.exams?.isNotEmpty() == true) {

                            Text(
                                "ЭК",
                                color = MaterialTheme.colorScheme.inverseSurface,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .clickable {

                                        if (!viewModel.headerText.value.contains(".")) {
                                            viewModel.getExams(viewModel.headerText.value)
                                        } else {
                                            val prefer =
                                                viewModel.context.getSharedPreferences(
                                                    Constants.ADDED_SCHEDULE,
                                                    Context.MODE_PRIVATE
                                                )
                                            val prepSet =
                                                prefer.getString(viewModel.headerText.value, "")
                                            Log.e("sdsdssd", prepSet ?: "s");
                                            viewModel.getExams(
                                                prepSet ?: ""
                                            )

                                        }

                                        navController.navigate("Exams")
                                    }
                                    .padding(end = 10.dp)
                            )
                        }
                    }
                },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.hide_icon),
                            contentDescription = "Выбрать расписание"
                        )
                    },
                    modifier = Modifier.clickable {
                        scope.launch {
                            bottomSheetState.show()
                        }
                    })
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                if (state.value.Days?.isNotEmpty() == true) {
                    ScheduleColumn(viewModel = viewModel)
                } else if (!state.value.isLoading) {
                    Text(
                        text = "Добавьте расписание...",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                if (state.value.error.isNotBlank()) {
                    Text(
                        text = state.value.error,
                        fontSize = 40.sp,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center, modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .padding(top = 70.dp)
                    )
                }
                if (state.value.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
