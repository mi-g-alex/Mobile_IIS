package by.g_alex.mobile_iis.presentation.schedule

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.schedule.navigation.Screen
import com.example.compose.presentation.list.component.addingPrepod
import com.example.compose.presentation.list.component.adding_newGroup
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

//import com.google.accompanist.pager.ExperimentalPagerApi
//import com.google.accompanist.pager.HorizontalPager
//import com.google.accompanist.pager.rememberPagerState

//@OptIn(ExperimentalPagerApi::class)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddNewScreen(viewModel: ScheduleViewModel, navController: NavController) {
    val searchtext = remember {
        mutableStateOf("")
    }
    val state = viewModel.groupState.value

    val TabItems = listOf("Groups","Prepods")
    val pagerState = rememberPagerState(
        pageCount = 2,
        initialPage = 0
    )
//    LaunchedEffect(Unit){
//        while (true){
//            yield()
//            delay(2000)
//            pagerState.animateScrollToPage(
//                page = 0,
//                animationSpec = tween(800)
//            )
//        }
//    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp).
                    clip(shape = AbsoluteRoundedCornerShape(0.dp,0.dp,10.dp,10.dp))
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
            HorizontalPager(state = pagerState
            ) {
                page->
                if(page==1){
                    addingPrepod(viewModel = viewModel,navController = navController)
                }
                else {
                    adding_newGroup(viewModel = viewModel, navController = navController)

                }
            }

        }

    }
}