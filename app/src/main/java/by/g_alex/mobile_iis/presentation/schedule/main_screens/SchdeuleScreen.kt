package by.g_alex.mobile_iis.presentation.schedule.main_screens
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R.drawable
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.additional_views.BottomSHIIT
import by.g_alex.mobile_iis.presentation.schedule.additional_views.ScheduleColumn
import kotlinx.coroutines.launch

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScheduleListScreen(
    viewModel: ScheduleViewModel,
    navController: NavController
) {
    val state = viewModel.state.value
    val coroutineScope = rememberCoroutineScope()
    val currentGroup = viewModel.headerText.value
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )
    val scope = rememberCoroutineScope()
    val barClickedState = remember {
        mutableStateOf(false)
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    // .height(300.dp)
                    .clip(shape = AbsoluteRoundedCornerShape(20.dp,20.dp,0.dp,0.dp))
                    .background(Color(0xff212121))

            ) {
                BottomSHIIT(
                    viewModel = viewModel,
                    navController = navController,
                    coroutineScope =coroutineScope,
                    sheetState = sheetState
                )
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp).
                    clip(shape = AbsoluteRoundedCornerShape(0.dp,0.dp,10.dp,10.dp))
                    .background(Color(0xff212121))
                    .padding(start = 10.dp, top = 10.dp)
            ) {
                Row(modifier = Modifier
                    // .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            barClickedState.value = !barClickedState.value
                            if (sheetState.isCollapsed) {
                                sheetState.expand()
                            } else {
                                sheetState.collapse()
                            }
                        }
                    }
                ) {
                    Image(
                        imageVector = if (barClickedState.value) ImageVector.vectorResource(drawable.hide)
                        else
                            ImageVector.vectorResource(drawable.show),
                        contentDescription = "qwertyui",
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                        // .padding(top = 15.dp, start = 15.dp)
                    )
                    Text(
                        text = currentGroup,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 6.dp),
                        color = Color.LightGray
                    )
                }
            }
            if (state.Days?.isNotEmpty() == true) {
                ScheduleColumn(viewModel = viewModel)
            } else if (!state.isLoading) {
                androidx.compose.material3.Text(
                    text = "No Schedule found((((....",
                    fontSize = 20.sp,
                    color = Color.LightGray,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )
            }
            if (state.error.isNotBlank()) {
                Text(
                    text = state.error,
                    fontSize = 40.sp,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center, modifier = Modifier
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
