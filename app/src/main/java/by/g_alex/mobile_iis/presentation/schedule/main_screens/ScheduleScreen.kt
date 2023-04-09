package by.g_alex.mobile_iis.presentation.schedule.main_screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.additional_views.BottomSheet
import by.g_alex.mobile_iis.presentation.schedule.additional_views.ScheduleColumn
import kotlinx.coroutines.launch


@Composable
fun ScheduleListScreen(
    viewModel: ScheduleViewModel,
    navController: NavController
) {

    BottomSheetScaffold(viewModel, navController)
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScaffold(
    viewModel: ScheduleViewModel,
    navController: NavController
) {
    val state = viewModel.state.value
    val currentGroup = viewModel.headerText.value
    val scope = rememberCoroutineScope()
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = {
            BottomSheet(
                viewModel = viewModel,
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
                    Row() {
                        Text(
                            text = currentGroup,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
                },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.hide),
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
                if (state.Days?.isNotEmpty() == true) {
                    ScheduleColumn(viewModel = viewModel)
                } else if (!state.isLoading) {
                    Text(
                        text = "No Schedule found((((....",
                        fontSize = 20.sp,
                    )
                }
                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        fontSize = 40.sp,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center, modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .padding(top = 70.dp)
                    )
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}
