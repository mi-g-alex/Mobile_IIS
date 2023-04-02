package by.g_alex.mobile_iis.presentation.schedule.main_screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.additional_views.AddingEmployee
import by.g_alex.mobile_iis.presentation.schedule.additional_views.AddingNewGroup
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddNewScreen(viewModel: ScheduleViewModel, navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Добавить расписание",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                })
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            val titles = listOf("Группы", "Преподаватели")
            Column {
                val pagerState: PagerState = rememberPagerState(initialPage = 0)
                val coroutineScope = rememberCoroutineScope()

                TabRow(

                    selectedTabIndex = pagerState.currentPage
                ) {
                    // Add tabs for all of our pages
                    titles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    pageCount = titles.size,
                    beyondBoundsPageCount = 2
                ) { page: Int ->

                    when (page) {
                        0 -> AddingNewGroup(viewModel = viewModel, navController = navController)
                        1 -> AddingEmployee(viewModel = viewModel, navController = navController)
                    }
                }
            }
        }
    }
}


