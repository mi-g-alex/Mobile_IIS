package by.g_alex.mobile_iis.presentation.grade_book_screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.data.remote.dto.grade_book.Lesson


@Composable
fun RatingScreen(
    viewModel: GradeBookViewModel = hiltViewModel(),
) {
    val gradeList = remember {
        viewModel.state
    }
    val diciplineList = mutableListOf("")
    if(gradeList.value.gradeBookState.isNotEmpty()){
        gradeList.value.gradeBookState[0].student.lessons.onEach {
            if (!diciplineList.contains(it.lessonNameAbbrev)) {
                diciplineList.add(it.lessonNameAbbrev)
                Log.e("LESSSOS", it.lessonNameAbbrev)
            }
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Рейтинг", fontSize = 20.sp) }
            )
        }
    ) {
        Column(modifier = Modifier
            .padding(it)
            .fillMaxSize())
        {

        }
    }
}