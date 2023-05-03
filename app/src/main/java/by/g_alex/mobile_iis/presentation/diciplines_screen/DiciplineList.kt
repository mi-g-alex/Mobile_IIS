package by.g_alex.mobile_iis.presentation.diciplines_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.g_alex.mobile_iis.data.remote.dto.diciplines.DiciplinesDto

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DiciplineList(
    diciplines: List<DiciplinesDto>
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (diciplines.isNotEmpty()) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(15.dp),
                    //horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "№", modifier = Modifier.weight(0.3f))
                    Text(text = "Дисциплина", modifier = Modifier.weight(1f))
                    Text(text = "Часы", modifier = Modifier.weight(0.2f))
                }
            }
        }
        items(diciplines) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                //horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = (diciplines.indexOf(it) + 1).toString(),
                    modifier = Modifier.weight(0.3f)
                )

                Text(text = it.name, modifier = Modifier.weight(1f))
                Text(text = it.hours.toString(), modifier = Modifier.weight(0.2f).padding(start = 5.dp))
            }
            Divider(
                color = MaterialTheme.colorScheme.inverseSurface, modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .height(1.dp)
                    .fillMaxWidth()
            )
        }
    }
}
