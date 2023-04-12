package by.g_alex.mobile_iis.presentation.mark_book.addtional

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.domain.model.profile.markbook_model.MarkBookMarkModel

@Composable
fun MarkBookListScreen(item: List<MarkBookMarkModel>, id: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            val average = mutableStateOf("")
            for(n in item){
                if(n.markPage == id) {
                    average.value = n.averageMark.toString()
                    break
                }
            }
            if (item[0].pagesSize != 0) {
                item {
                    Text(
                        text = "Средний балл за семестр: ${average.value}",
                        fontSize = 25.sp,
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                items(item){
                    MarkBookItem(mark = it,id)
                }
            }
        }

    }
}