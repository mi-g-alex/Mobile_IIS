package by.g_alex.mobile_iis.presentation.rating_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavController
import by.g_alex.mobile_iis.data.remote.dto.rating.RatingDto

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RatingColumn(
    students: List<RatingDto>?,
    navController: NavController,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        if (!students.isNullOrEmpty()) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(15.dp),
                    //horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(text = "№", modifier = Modifier.weight(0.3f))
                    Text(text = "Студ. билет", modifier = Modifier.weight(1f))
                    Text(text = "Cр. балл", modifier = Modifier.weight(0.7f))
                    Text(text = "Часы", modifier = Modifier.weight(0.4f))
                }
            }
        }
        items(students ?: emptyList()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate("personalRating/${it.studentCardNumber}")
                    }
                    .padding(15.dp),
                //horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = (students!!.indexOf(it) + 1).toString(),
                    modifier = Modifier.weight(0.3f)
                )

                Text(text = it.studentCardNumber.toString(), modifier = Modifier.weight(1f))

                Text(text = it.average.toString(), modifier = Modifier.weight(0.7f))

                Text(text = it.hours.toString(), modifier = Modifier.weight(0.4f))
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