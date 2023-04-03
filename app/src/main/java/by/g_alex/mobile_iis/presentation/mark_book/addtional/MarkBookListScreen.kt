package by.g_alex.mobile_iis.presentation.mark_book.addtional

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.data.remote.dto.mark_book.MarkBookDto

@Composable
fun MarkBookListScreen(item: MarkBookDto, id: Int) {
    val a = item.markPages[id]
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            if (a?.marks?.size != null) {
                item {
                    Text(
                        text = "Средний балл: " + a.averageMark.toString(),
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(10.dp)
                    )
                }

                for (i in a.marks.indices) {
                    item {
                        MarkBookItem(mark = a.marks[i])
                    }
                }
            }
        }

    }
}