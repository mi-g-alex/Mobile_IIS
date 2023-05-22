package by.g_alex.mobile_iis.presentation.dormitory_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DormitoryScreen(viewModel: DormitoryViewModel = hiltViewModel()) {
    val titles = mutableListOf("Заселение", "Льготы")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Общежитие", fontSize = 20.sp) }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val pagerState: PagerState =
                        rememberPagerState(0)
                    val coroutineScope = rememberCoroutineScope()
                    TabRow(
                        selectedTabIndex = pagerState.currentPage
                    ) {
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
                        beyondBoundsPageCount = 2,
                        modifier = Modifier.fillMaxSize()
                    ) { page: Int ->
                        when (page) {
                            0 -> DormList()
                            1 -> PrivilegeList()
                        }
                    }
                }
            if (viewModel.state.value.error.isNotBlank()) {
                if (viewModel.state.value.error == "LessCookie") {
                    Box(modifier = Modifier.align(Alignment.Center)) {
                        Column(modifier = Modifier) {
                            Text(
                                text = "Сначала войдите в аккаунт...",
                                fontSize = 25.sp
                            )
                        }
                    }
                }
            }

            if (viewModel.state.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
