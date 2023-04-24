package by.g_alex.mobile_iis.presentation.dormitory_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import java.util.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DormitoryScreen() {
    val titles = mutableListOf("Заселение", "Льготы")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Общежитие", fontSize = 20.sp) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
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
    }
}