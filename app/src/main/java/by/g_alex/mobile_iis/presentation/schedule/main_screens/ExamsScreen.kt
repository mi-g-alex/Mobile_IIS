package by.g_alex.mobile_iis.presentation.schedule.main_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.lists_items.LessonItem

@Composable
fun ExamsScreen(
    viewModel: ScheduleViewModel
) {
    val state = viewModel.exState
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Экзамены")
                },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if(state.value.isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(state.value.exams ?: emptyList()) {
                        Text(
                            text = it.dateEnd ?: "",
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                        LessonItem(schedule = it, week = 0)
                    }

                }
            }
        }
    }
}