package by.g_alex.mobile_iis.presentation.schedule.lists_items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.data.local.entity.LessonModel


@Composable
fun LessonItem(
    schedule: LessonModel,
    week: Int
) {
    if (schedule.weekNumber != null) {
        for (n in schedule.weekNumber.indices) {
            if (schedule.weekNumber[n] == week)
                break
            if (n == schedule.weekNumber.size - 1)
                return
        }
    }
    Card(
        shape = AbsoluteRoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor =  Color(0xff212121),
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .height(60.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {

        Row() {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = schedule.startLessonTime.toString(),
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 13.dp, start = 10.dp)
                )
                Text(
                    text = schedule.endLessonTime.toString(),
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            val barColor: Color
            if (schedule.lessonTypeAbbrev == "ЛК") barColor = Color.Green
            else if (schedule.lessonTypeAbbrev == "ЛР") barColor = Color.Red
            else barColor = Color.Yellow
            Box(
                modifier = Modifier
                    .width(3.dp)
                    .height(60.dp)
                    .padding(top = 10.dp, bottom = 10.dp)
                    .clip(shape = AbsoluteRoundedCornerShape(40.dp))
                    .background(barColor)
            )
            val audit = remember { mutableStateOf("") }
            if (schedule.auditories?.size!! > 0)
                audit.value = schedule.auditories[0]
            Column() {

                Text(
                    text = (schedule.subject ?: schedule.note ?: "Хз че тут"),
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, end = 0.dp, top = if(schedule.note==null)13.dp
                    else 8.dp)
                )
                Text(
                    text = audit.value,//+ if(schedule.numSubgroup!=0){"  (subgr. "+schedule.numSubgroup+")"}else{""},
                    fontSize = 10.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, end = 0.dp, bottom = 0.dp)
                )
                Text(
                    text = if(schedule.subject!=null)schedule.note?:""
                    else "",
                    fontSize = 9.sp,
                    color = Color.LightGray,
                    modifier = Modifier.padding(start = 10.dp, end = 0.dp, bottom = 0.dp)
                )
            }
            val shit = remember { mutableStateOf("") }
            if (schedule.weekNumber != null) {
                shit.value = "Нед. "
                for (n in schedule.weekNumber.toString()) {
                    if (n != '[' && n != ']')
                        shit.value += n
                }
                if (schedule.weekNumber.size == 4) shit.value = ""
            }
                Text(
                    text = schedule.fio + "\n" + shit.value,
                    fontSize = 12.sp,
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 9.dp, top = 10.dp),
                    textAlign = TextAlign.End,
                )

        }

    }
}
