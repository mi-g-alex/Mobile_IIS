package by.g_alex.mobile_iis.presentation.schedule.lists_items

import android.graphics.Paint.Align
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.data.local.entity.LessonModel


@Composable
fun LessonItem(
    schedule: LessonModel,
    week: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .height(60.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(modifier = Modifier.weight(40f)) {
            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .fillMaxWidth(0.13f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = schedule.startLessonTime.toString(),
                )
                Text(
                    text = schedule.endLessonTime.toString(),
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            val barColor: Color = when (schedule.lessonTypeAbbrev) {
                "ЛК" -> Color.Green
                "ЛР" -> Color.Red
                "ПЗ" -> Color.Yellow
                else -> Color.LightGray
            }
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxHeight(0.85f)
                    .align(Alignment.CenterVertically)
                    .padding()
                    .clip(shape = AbsoluteRoundedCornerShape(40.dp))
                    .background(barColor)
            )

            var audit = ""
            for (i in schedule.auditories!!) {
                audit += i
                if (i != schedule.auditories.last()) audit += ", "
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = (schedule.subject ?: schedule.note ?: ""),
                    fontSize = 18.sp
                )
                if (audit.isNotBlank()) Text(
                    text = audit,
                    fontSize = 10.sp,
                )
                if (schedule.subject != null)
                    if (schedule.note != null) Text(
                        text = schedule.note,
                        fontSize = 10.sp,
                    )
            }

            val weeks = remember { mutableStateOf("") }

            if (schedule.weekNumber != null) {
                weeks.value = "Нед. "
                if (schedule.weekNumber.size == 4) weeks.value = ""
                else for (n in schedule.weekNumber) {
                    weeks.value += n
                    if (n != schedule.weekNumber.last()) weeks.value += ", "
                }
            }

            if (schedule.numSubgroup != 0) weeks.value += " (Подгр. ${schedule.numSubgroup})"

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 10.dp)
                    .align(Alignment.CenterVertically)
            ) {
                schedule.fio?.let {
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = it,
                        fontSize = 16.sp
                    )
                }
                if (weeks.value.isNotBlank()) {
                    Text(
                        modifier = Modifier.align(Alignment.End),
                        text = weeks.value,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun preview() {
    LessonItem(
        schedule = LessonModel(
            key = 0,
            id = "2345678",
            auditories = listOf(),
            endLessonTime = "12.21",
            lessonTypeAbbrev = "Л",
            numSubgroup = 1,
            startLessonTime = "09:00",
            subject = "ОАиП",
            subjectFullName = "Основы алгоритмизации и программирования",
            weekNumber = listOf(1, 2, 3, 4),
            fio = "Владымцев В. Д.",
            note = null,
            weekDay = "Понедельник"
        ),
        1
    )
}
