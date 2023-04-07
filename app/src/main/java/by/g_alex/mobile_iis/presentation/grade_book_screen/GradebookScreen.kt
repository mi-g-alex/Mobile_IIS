package by.g_alex.mobile_iis.presentation.grade_book_screen

import android.util.Log
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
import by.g_alex.mobile_iis.presentation.grade_book_screen.additional.TabLayout
import kotlinx.coroutines.launch

data class Dicipline(
    val nameAbbv: String,
    var pzH: Int,
    var pzM: Int,
    var lkH: Int,
    var lkM: Int,
    var lbH: Int,
    var lbM: Int
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RatingScreen(
    gradeBookViewModel: GradeBookViewModel = hiltViewModel()
) {
    val state = gradeBookViewModel.state.value
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Рейтинг", fontSize = 20.sp) }
            )
        }
    ) {

        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            Log.e("~~~~~", state.toString())
            if (state.gradeBookState != null) {
                val titles = listOf("Итого", "01.03", "01.04", "01.05", "Вне")
                val pagerState: PagerState = rememberPagerState(initialPage = 0)
                val coroutineScope = rememberCoroutineScope()
                Column {
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
                        beyondBoundsPageCount = 5
                    ) { page: Int ->
                        when (page) {
                            1, 2, 3 -> coroutineScope.apply { TabLayout(type = titles[page] + ".2023") }
                            0, 4 -> coroutineScope.apply { TabLayout(type = titles[page]) }
                        }
                    }

                }
            }
            if (state.error.isNotBlank()) {
                Text(text = state.error)
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}