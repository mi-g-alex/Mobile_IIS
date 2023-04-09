package by.g_alex.mobile_iis.presentation.grade_book_screen.additional

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.presentation.grade_book_screen.Dicipline
import by.g_alex.mobile_iis.presentation.grade_book_screen.GradeBookViewModel

@Composable
fun TabLayout(
    viewModel: GradeBookViewModel = hiltViewModel(),
    type : String
){
    val gradeList = remember {
        viewModel.state
    }
    val diciplineList = mutableMapOf<String, Dicipline>()
    if (gradeList.value.gradeBookState!!.isNotEmpty()) {
        gradeList.value.gradeBookState!![0].student.lessons.onEach {
            if (!diciplineList.keys.contains(it.lessonNameAbbrev)) {
                diciplineList.put(
                    it.lessonNameAbbrev,
                    Dicipline(it.lessonNameAbbrev, 0, 0, 0, 0, 0, 0)
                )
                Log.e("LESSSOS", it.lessonNameAbbrev)
            }
            if(type == "Итого" || type == it.controlPoint) {
                when (it.lessonTypeId) {
                    2 -> {
                        diciplineList[it.lessonNameAbbrev]!!.lkM += it.marks.count()
                        diciplineList[it.lessonNameAbbrev]!!.lkH += it.gradeBookOmissions
                    }
                    3 -> {
                        diciplineList[it.lessonNameAbbrev]!!.pzM += it.marks.count()
                        diciplineList[it.lessonNameAbbrev]!!.pzH += it.gradeBookOmissions
                    }
                    4 -> {
                        diciplineList[it.lessonNameAbbrev]!!.lbM += it.marks.count()
                        diciplineList[it.lessonNameAbbrev]!!.lbH += it.gradeBookOmissions
                    }
                }
            }
        }
    }
    Column(
        modifier = Modifier
            //.padding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    )
    {

        diciplineList.onEach { dicipline ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .fillMaxWidth()
            )
            {
                Text(
                    text = dicipline.key,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp)
                )
                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth()
                        .height(1.dp)
                        .clip(shape = RoundedCornerShape(40.dp))
                        .background(MaterialTheme.colorScheme.inverseSurface)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column() {
                        Text(text = "ЛК")
                        Text(text = dicipline.value.lkM.toString() + " шт.")
                        Text(text = dicipline.value.lkH.toString() + " ч.")
                    }
                    Column() {
                        Text(text = "ПЗ")
                        Text(text = dicipline.value.pzM.toString() + " шт.")
                        Text(text = dicipline.value.pzH.toString() + " ч.")
                    }
                    Column() {
                        Text(text = "ЛР")
                        Text(text = dicipline.value.lbM.toString() + " шт.")
                        Text(text = dicipline.value.lbH.toString() + " ч.")
                    }
                }
            }
        }
    }
}