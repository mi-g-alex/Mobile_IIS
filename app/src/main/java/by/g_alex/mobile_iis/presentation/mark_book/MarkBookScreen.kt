package by.g_alex.mobile_iis.presentation.mark_book

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.mark_book.addtional.MarkBookListScreen
import by.g_alex.mobile_iis.presentation.schedule.additional_views.AddingEmployee
import by.g_alex.mobile_iis.presentation.schedule.additional_views.AddingNewGroup
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch
import kotlin.math.sign

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MarkBookScreen(
    viewModel: MarkBookViewModel = hiltViewModel(),
) {

    //viewModel.getMarkBook()
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
                    } else {
                        Text(
                            text = "Загрузка...",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            if(state.markBookState != null){
                val titles = mutableListOf<Int>()
                for(i in 1..state.markBookState.markPages.size) titles.add(i)
                Column {
                    val pagerState: PagerState = rememberPagerState(initialPage = titles.size - 2)
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
                        MarkBookListScreen(item = state.markBookState, id = page + 1)
                    }
                }
            }
        }
    }
}

