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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import by.g_alex.mobile_iis.data.remote.dto.rating.RatingDto

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RatingColumn(
    students: List<RatingDto>?,
    navController: NavController,
) {

    val searchText = remember { mutableStateOf(TextFieldValue("")) }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        if (!students.isNullOrEmpty()) {
            item {
                OutlinedTextField(
                    value = searchText.value,
                    shape = MaterialTheme.shapes.large,
                    onValueChange = { newText ->
                        searchText.value = newText
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = "Search"
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp)

                )
            }
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(15.dp),
                ) {
                    Text(text = "№", modifier = Modifier.weight(0.3f))
                    Text(text = "Студ. билет", modifier = Modifier.weight(1f))
                    Text(text = "Cр. балл", modifier = Modifier.weight(0.7f))
                    Text(text = "Часы", modifier = Modifier.weight(0.4f))
                }

            }
        }
        if (students != null) {
            items(students.size) { it ->
                if(students[it].studentCardNumber?.contains(searchText.value.text) == true) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("personalRating/${students[it].studentCardNumber}")
                            }
                            .padding(15.dp),
                        //horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            text = (it + 1).toString(),
                            modifier = Modifier.weight(0.3f)
                        )

                        Text(
                            text = students[it].studentCardNumber.toString(),
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = students[it].average.toString(),
                            modifier = Modifier.weight(0.7f)
                        )

                        Text(text = students[it].hours.toString(), modifier = Modifier.weight(0.4f))
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
    }
}