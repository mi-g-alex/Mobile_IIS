package by.g_alex.mobile_iis.presentation.schedule.lists_items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.data.local.entity.LessonModel


@Composable
fun LessonItem(
    schedule: LessonModel,
    week: Int
) {
    val openDialog = remember { mutableStateOf(false) }
    val dialogTitle = remember { mutableStateOf("") }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .height(60.dp)
            .clickable {
                openDialog.value = true
                dialogTitle.value = schedule.subjectFullName ?: schedule.note
                        ?: "Хз че за дичь такого не может быть"
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
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
                    fontSize = 14.sp
                )
                Text(
                    text = schedule.endLessonTime.toString(),
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.padding(5.dp))
            val barColor: Color = when (schedule.lessonTypeAbbrev) {
                "ЛК" -> Color.Green
                "ЛР" -> Color.Red
                "ПЗ" -> Color.Yellow
                "Экзамен" -> Color.Red
                "Консультация" -> Color.Green
                else -> Color.LightGray
            }
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight(0.75f)
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
                    fontSize = 16.sp
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
                if (schedule.type) {
                    schedule.fio?.let {
                        Text(
                            modifier = Modifier.align(Alignment.End),
                            text = it,
                            fontSize = 12.sp
                        )
                    }
                } else {
                    schedule.groupNum.let {
                        Text(
                            modifier = Modifier.align(Alignment.End),
                            text = it,
                            fontSize = 10.sp
                        )
                    }
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
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(text = dialogTitle.value + "(" + schedule.lessonTypeAbbrev + ")") },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(schedule.startLessonTime + "-" + schedule.endLessonTime)
                        Text(
                            "Нед. " + schedule.weekNumber.toString().removePrefix("[")
                                .removeSuffix("]")
                        )
                    }
                    if (schedule.auditories?.isNotEmpty() == true) Text(
                        schedule.auditories[0]
                    )
                    Text(schedule.fio ?: "")
                    Text(schedule.groupNum)
                    Text(schedule.note ?: "")
                }
            },
            confirmButton = {
                Button(

                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Ok")
                }
            }
        )
    }
}

