package by.g_alex.mobile_iis.presentation.schedule.main_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import by.g_alex.mobile_iis.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.navigation.Screen
import by.g_alex.mobile_iis.presentation.schedule.additional_views.AddingEmployee
import by.g_alex.mobile_iis.presentation.schedule.additional_views.AddingNewGroup
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddNewScreen(viewModel: ScheduleViewModel, navController: NavController) {
    val searchtext = remember {
        mutableStateOf("")
    }
    val state = viewModel.groupState.value

    val TabItems = listOf("Groups", "Prepods")
    val pagerState = rememberPagerState(
        pageCount = 2,
        initialPage = 0
    )
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(shape = AbsoluteRoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp))
                    .background(Color(0xff212121))
                    .padding(start = 10.dp, top = 10.dp)
            ) {

                Row() {
                    Image(imageVector = ImageVector.vectorResource(R.drawable.back_arrow),
                        contentDescription = "sdcsdc",
                        modifier = Modifier
                            .padding(top = 8.dp, end = 15.dp, start = 5.dp)
                            .size(25.dp, 25.dp)
                            .clickable { navController.navigate(Screen.Home.route) })

                    Text(
                        text = "Add new Scheadule",
                        color = Color.LightGray,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            TabRow(selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.Black,
                modifier = Modifier.padding(15.dp),
                indicator = { tabPositions ->
                    Modifier.pagerTabIndicatorOffset(
                        pagerState = pagerState,
                        tabPositions = tabPositions
                    )
                }
            ) {
                Text(
                    text = "Groups",
                    color = if (pagerState.currentPage == 0) Color.LightGray
                    else Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable { coroutineScope.launch {pagerState.animateScrollToPage(0)} }
                )
                Text(
                    text = "Employees",
                    color = if (pagerState.currentPage == 1) Color.LightGray
                    else Color.DarkGray,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable { coroutineScope.launch {pagerState.animateScrollToPage(1)} }
                )
            }

            HorizontalPager(
                state = pagerState
            ) { page ->
                if (page == 1) {
                    AddingEmployee(viewModel = viewModel, navController = navController)
                } else {
                    AddingNewGroup(viewModel = viewModel, navController = navController)

                }
            }

        }

    }
}