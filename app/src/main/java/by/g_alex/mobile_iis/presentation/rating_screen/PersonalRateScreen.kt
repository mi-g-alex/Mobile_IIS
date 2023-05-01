package by.g_alex.mobile_iis.presentation.rating_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.grade_book_screen.Average
import by.g_alex.mobile_iis.presentation.grade_book_screen.Dicipline
import by.g_alex.mobile_iis.presentation.grade_book_screen.additional.TabLayout
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonalRateScreen(
    viewModel: RatingAllViewModel = hiltViewModel(),
    navController: NavController,
    number: String
) {
    if(viewModel.prState.value.PersonalState==null) {
        viewModel.getPersonalRating(number)
    }
    val gradeList = remember {
        viewModel.prState
    }
    val titles = mutableListOf("Итого")
    val titlesForTab = mutableListOf("Итого")
    val ktAverage = mutableListOf<Average>(
        Average(0, 0, 0.0),
        Average(0, 0, 0.0),
        Average(0, 0, 0.0),
        Average(0, 0, 0.0),
        Average(0, 0, 0.0)
    )
    val diciplineList = mutableListOf(
        mutableMapOf<String, Dicipline>(),
        mutableMapOf<String, Dicipline>(),
        mutableMapOf<String, Dicipline>(),
        mutableMapOf<String, Dicipline>(),
        mutableMapOf<String, Dicipline>()
    )
    if (gradeList.value.PersonalState != null) {
        gradeList.value.PersonalState!!.lessons.onEach {
            if (!titles.contains(it.controlPoint)) {
                titles.remove("Вне КТ")
                titles.add(it.controlPoint)
                titlesForTab.add(it.controlPoint.removeSuffix(".2023"))
                titles.add("Вне КТ")
            }
            ktAverage[0].hours += it.gradeBookOmissions
            ktAverage[titles.indexOf(it.controlPoint)].hours += it.gradeBookOmissions

            ktAverage[0].markCount += it.marks.size
            ktAverage[titles.indexOf(it.controlPoint)].markCount += it.marks.size
            ktAverage[0].markSum += it.marks.sum()
            ktAverage[titles.indexOf(it.controlPoint)].markSum += it.marks.sum()
            if (!diciplineList[0].keys.contains(it.lessonNameAbbrev)) {

                diciplineList.onEach { kt ->
                    kt[it.lessonNameAbbrev] = Dicipline(
                        it.lessonNameAbbrev,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        mutableListOf(),
                        mutableListOf(),
                        mutableListOf()
                    )
                }
            }
            val lesos = mutableStateOf(0)
            for (n in titles.indices) {
                if (titles[n] == it.controlPoint) {
                    lesos.value = n
                    break
                }
            }

            when (it.lessonTypeId) {
                2 -> {
                    diciplineList[0][it.lessonNameAbbrev]!!.lkM += it.marks.count()
                    diciplineList[0][it.lessonNameAbbrev]!!.lkH += it.gradeBookOmissions
                    if (lesos.value > 0) {
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.lkM += it.marks.count()
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.lkH += it.gradeBookOmissions
                    }
                    it.marks.onEach { mark ->
                        diciplineList[0][it.lessonNameAbbrev]!!.lkMarks.add(
                            mark
                        )
                        if (lesos.value > 0) {
                            diciplineList[lesos.value][it.lessonNameAbbrev]!!.lkMarks.add(
                                mark
                            )
                        }
                    }
                }

                3 -> {
                    diciplineList[0][it.lessonNameAbbrev]!!.pzM += it.marks.count()
                    diciplineList[0][it.lessonNameAbbrev]!!.pzH += it.gradeBookOmissions
                    if (lesos.value > 0) {
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.pzM += it.marks.count()
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.pzH += it.gradeBookOmissions
                    }
                    it.marks.onEach { mark ->
                        diciplineList[0][it.lessonNameAbbrev]!!.pzMarks.add(
                            mark
                        )
                        if (lesos.value > 0) {
                            diciplineList[lesos.value][it.lessonNameAbbrev]!!.pzMarks.add(
                                mark
                            )
                        }
                    }

                }

                4 -> {
                    diciplineList[0][it.lessonNameAbbrev]!!.lbM += it.marks.count()
                    diciplineList[0][it.lessonNameAbbrev]!!.lbH += it.gradeBookOmissions
                    if (lesos.value > 0) {
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.lbM += it.marks.count()
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.lbH += it.gradeBookOmissions
                    }
                    it.marks.onEach { mark ->
                        diciplineList[0][it.lessonNameAbbrev]!!.lrMarks.add(
                            mark
                        )
                        if (lesos.value > 0) {
                            diciplineList[lesos.value][it.lessonNameAbbrev]!!.lrMarks.add(
                                mark
                            )
                        }
                    }
                }
            }
        }
    }
    titlesForTab.add("Вне КТ")

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Рейтинг " + number,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 17.sp
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
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            if (viewModel.prState.value.PersonalState != null) {

                val pagerState: PagerState = rememberPagerState(initialPage = 0)
                val coroutineScope = rememberCoroutineScope()
                Column {
                    TabRow(
                        selectedTabIndex = pagerState.currentPage
                    ) {
                        titlesForTab.forEachIndexed { index, title ->
                            Tab(
                                text = { Text(title) },
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
//                               modifier = Modifier.background(MaterialTheme.colorScheme.background),
//                                unselectedContentColor = MaterialTheme.colorScheme.inverseSurface,
//                                selectedContentColor = MaterialTheme.colorScheme.primary,

                            )
                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        pageCount = titles.size,
                        beyondBoundsPageCount = 2
                    ) { page: Int ->
                        coroutineScope.apply {
                            TabLayout(
                                currentList = diciplineList[page],
                                average = ktAverage[page]
                            )
                        }
                    }

                }
            }

            if (viewModel.prState.value.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

