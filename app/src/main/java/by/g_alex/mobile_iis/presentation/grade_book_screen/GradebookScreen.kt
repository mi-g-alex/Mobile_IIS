package by.g_alex.mobile_iis.presentation.grade_book_screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    var lbM: Int,
    val pzMarks: MutableList<Int>,
    val lkMarks: MutableList<Int>,
    val lrMarks: MutableList<Int>
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RatingScreen(
    gradeBookViewModel: GradeBookViewModel = hiltViewModel()
) {
    val state = gradeBookViewModel.state.value
    val gradeList = remember {
        gradeBookViewModel.state
    }
    val titles = mutableListOf("Итого")
    val titlesForTab = mutableListOf("Итого")
    val diciplineList = remember{mutableListOf(
        mutableMapOf<String, Dicipline>(),
        mutableMapOf<String, Dicipline>(),
        mutableMapOf<String, Dicipline>(),
        mutableMapOf<String, Dicipline>(),
        mutableMapOf<String, Dicipline>()
    )}
    Log.e("START","START")
    if (gradeList.value.gradeBookState?.isNotEmpty() == true) {
        gradeList.value.gradeBookState!![0].student.lessons.onEach {
            if(!titles.contains(it.controlPoint)){
                titles.remove("Вне КТ")
                titles.add(it.controlPoint)
                titlesForTab.add(it.controlPoint.removeSuffix(".2023"))
                titles.add("Вне КТ")
            }
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
            for(n in titles.indices){
                if(titles[n] == it.controlPoint) {
                    lesos.value = n
                    break
                }
            }

            when (it.lessonTypeId) {
                2 -> {
                    diciplineList[0][it.lessonNameAbbrev]!!.lkM += it.marks.count()
                    diciplineList[0][it.lessonNameAbbrev]!!.lkH += it.gradeBookOmissions
                    if(lesos.value>0){
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.lkM += it.marks.count()
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.lkH += it.gradeBookOmissions
                    }
                    it.marks.onEach { mark ->
                        diciplineList[0][it.lessonNameAbbrev]!!.lkMarks.add(
                            mark
                        )
                        if(lesos.value>0){
                            diciplineList[lesos.value][it.lessonNameAbbrev]!!.lkMarks.add(
                                mark
                            )
                        }
                    }
                }
                3 -> {
                    diciplineList[0][it.lessonNameAbbrev]!!.pzM += it.marks.count()
                    diciplineList[0][it.lessonNameAbbrev]!!.pzH += it.gradeBookOmissions
                    if(lesos.value>0){
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.pzM += it.marks.count()
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.pzH += it.gradeBookOmissions
                    }
                    it.marks.onEach { mark ->
                        diciplineList[0][it.lessonNameAbbrev]!!.pzMarks.add(
                            mark
                        )
                        if(lesos.value>0){
                            diciplineList[lesos.value][it.lessonNameAbbrev]!!.pzMarks.add(
                                mark
                            )
                        }
                    }

                }
                4 -> {
                    diciplineList[0][it.lessonNameAbbrev]!!.lbM += it.marks.count()
                    diciplineList[0][it.lessonNameAbbrev]!!.lbH += it.gradeBookOmissions
                    if(lesos.value>0){
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.lbM += it.marks.count()
                        diciplineList[lesos.value][it.lessonNameAbbrev]!!.lbH += it.gradeBookOmissions
                    }
                    it.marks.onEach { mark ->
                        diciplineList[0][it.lessonNameAbbrev]!!.lrMarks.add(
                            mark
                        )
                        if(lesos.value>0){
                            diciplineList[lesos.value][it.lessonNameAbbrev]!!.lrMarks.add(
                                mark
                            )
                        }
                    }
                }
            }
        }
    }
    Log.e("FINISH","FINISH")
    titlesForTab.add("Вне КТ")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Рейтинг", fontSize = 20.sp) }
            )
        }
    ) {

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (state.gradeBookState != null) {

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
                            )
                        }
                    }
                    HorizontalPager(
                        state = pagerState,
                        pageCount = titles.size,
                        beyondBoundsPageCount = 5
                    ) { page: Int ->
                        coroutineScope.apply {
                            Log.e("Page",diciplineList[0].toString())
                            TabLayout(
                               currentList = diciplineList[page]
                            )
                        }
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