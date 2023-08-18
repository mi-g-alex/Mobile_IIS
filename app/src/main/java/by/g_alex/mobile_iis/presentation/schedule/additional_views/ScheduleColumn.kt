package by.g_alex.mobile_iis.presentation.schedule.additional_views

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.data.local.entity.LessonModel
import by.g_alex.mobile_iis.presentation.schedule.ScheduleViewModel
import by.g_alex.mobile_iis.presentation.schedule.lists_items.LessonItem
import java.time.LocalDate

@Composable
fun ScheduleColumn(viewModel: ScheduleViewModel = hiltViewModel()) {
    val weekState = remember { mutableStateOf(viewModel.weekState.value) }
    val currentGroup = remember {
        mutableStateOf(viewModel.headerText.value)
    }
   
    val currentSchedule = viewModel.state.value.Days ?: emptyList()
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val date = mutableStateOf(LocalDate.now())
        date.value = LocalDate.now()

        var upcnt = weekState.value.week ?: 1
        var txt: String
        val mutlist = mutableStateOf(mutableListOf<LessonModel>())
        while ((date.value.month.value < 6 || (date.value.month.value in 8..12)) && currentGroup.value != "None") {
            mutlist.value = mutableListOf<LessonModel>()
            val downDate = mutableStateOf(date.value)
            var cnt = upcnt
            var month = ""

            if(date.value.month.value == 8){
                while(date.value.month.value!=9){

                    date.value = date.value.plusDays(1)

                }
                cnt = 1
                upcnt = 1
            }


            for (n in date.value.month.toString().indices)
                if (n > 0) month += date.value.month.toString()[n].lowercase()
                else month += date.value.month.toString()[n]

            var dayOfweek = ""
            for (n in date.value.dayOfWeek.toString().indices)
                if (n > 0) dayOfweek += date.value.dayOfWeek.toString()[n].lowercase()
                else dayOfweek += date.value.dayOfWeek.toString()[n]

            txt =
                month + " " + date.value.dayOfMonth.toString() + ", " + dayOfweek + ", week " + cnt.toString()

            for (n in currentSchedule) {
                if (n.weekDay == downDate.value.dayOfWeek.toString() && ((n.weekNumber?.contains(
                        cnt
                    ) == true) || (n.weekNumber == null))
                ) {
                    if(n.lessonTypeAbbrev == "Экзамен" || n.lessonTypeAbbrev == "Консультация"){
                        continue
                    }
                    mutlist.value.add(n)

                } //else if (n.weekDay != downDate.value.dayOfWeek.toString()) firstStep = false

            }
            if(mutlist.value.isNotEmpty()){
                items(mutableListOf(txt)) {
                    Text(text = it,
                        modifier = Modifier.padding(
                            start = 20.dp,
                            top = 10.dp,
                            bottom = 0.dp
                        )
                    )
                }
            }
            items(mutlist.value) { iter ->
                LessonItem(schedule = iter, week = cnt)
            }
            if (date.value.dayOfWeek.toString() != "SATURDAY" && date.value.dayOfWeek.toString() != "SUNDAY") date.value =
                date.value.plusDays(1)
            else if (date.value.dayOfWeek.toString() == "SUNDAY") {
                date.value = date.value.plusDays(1)
                upcnt++;upcnt %= 5
                if (upcnt == 0) upcnt++
            } else {
                date.value = date.value.plusDays(2)
                upcnt++;upcnt %= 5
                if (upcnt == 0) upcnt++
            }
        }
    }
}