package by.g_alex.mobile_iis.presentation.mark_book.addtional

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.data.remote.dto.mark_book.MarkBookDto

@Composable
fun MarkBookItem(mark: MarkBookDto.MapValue.Mark) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Column {
            Text(
                text = mark.subject,
                fontSize = 40.sp,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = mark.formOfControl + " | " + mark.hours + "ч.",
                fontSize = 25.sp,
                modifier = Modifier.padding(5.dp)
            )

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
            )

            if (mark.commonRetakes != null) {
                Text(
                    text = "Пересдач: " + mark.retakesCount.toString()
                            + " (" + String.format("%.2f", mark.commonRetakes * 100) + "%)",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            } else {
                Text(
                    text = "Пересдач: " + mark.retakesCount.toString(),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

            if (mark.commonMark != null)
                Text(
                    text = "Отметка: " + mark.mark
                            + " (" + String.format("%.2f", mark.commonMark) + ")",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            else {
                Text(
                    text = "Отметка: " + mark.mark,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}