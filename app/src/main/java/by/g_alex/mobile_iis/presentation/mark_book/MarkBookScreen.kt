package by.g_alex.mobile_iis.presentation.mark_book

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.presentation.mark_book.addtional.MarkBookListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MarkBookScreen(
    viewModel: MarkBookViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    if (state.markBookState?.averageMark != null) {
                        Text(
                            text = "Общий средний балл: " +
                                    state.markBookState.averageMark.toString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (state.markBookState != null) {
                val titles = mutableListOf<Int>()
                for (i in 1..state.markBookState.markPages.size) titles.add(i)
                if (titles.size == 0) {
                    Text(
                        text = "Тут пока пусто",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                } else
                    Column {
                        val pagerState: PagerState =
                            rememberPagerState(initialPage = titles.size - 1)
                        val coroutineScope = rememberCoroutineScope()
                        TabRow(
                            selectedTabIndex = pagerState.currentPage
                        ) {
                            // Add tabs for all of our pages
                            titles.forEachIndexed { index, title ->
                                Tab(
                                    text = { Text(title.toString()) },
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
                            beyondBoundsPageCount = state.markBookState.markPages.size
                        ) { page: Int ->
                            MarkBookListScreen(item = state.markBookState, id = (page + 1))
                        }
                    }
            }
            if (state.error.isNotBlank()) {
                if (state.error == "LessCookie") {
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

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

