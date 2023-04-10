package by.g_alex.mobile_iis.presentation.mark_book.addtional

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(
                    text = mark.subject,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = mark.mark,
                    fontSize = 30.sp,
                    modifier = Modifier.padding(5.dp),
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text(
                    text = mark.formOfControl + " | " + mark.hours + "ч.",
                    modifier = Modifier.padding(5.dp),
                    fontSize = 20.sp
                )
                mark.date?.let {
                    Text(
                        text = it,
                        modifier = Modifier.padding(5.dp),
                        fontSize = 20.sp
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                color = MaterialTheme.colorScheme.inverseSurface
            )

            if (mark.teacher != null) {
                Text(
                    text = mark.teacher,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )
            }

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
                    text = "Cр. балл за 4 года: " + String.format("%.2f", mark.commonMark),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(5.dp)
                )

        }
    }
}