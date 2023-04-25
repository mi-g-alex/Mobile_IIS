package by.g_alex.mobile_iis.presentation.grade_book_screen.additional

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.mobile_iis.presentation.grade_book_screen.Average
import by.g_alex.mobile_iis.presentation.grade_book_screen.Dicipline

@Composable
fun TabLayout(
    currentList : MutableMap<String,Dicipline>,
    average: Average
) {

    val openDialog = remember { mutableStateOf(false) }
    val dialogTitle = remember{ mutableStateOf("") }
    val diciplineObject = remember{ mutableStateOf( Dicipline("", 0, 0, 0,0,0, 0, mutableListOf(), mutableListOf(),
        mutableListOf()
    )) }

    LazyColumn(
        modifier = Modifier
            //.padding()
            .fillMaxSize()
    )
    {
        val diciplineList = mutableStateOf(currentList.values.toList() )
        val aver = if(average.markCount>0 && (average.markSum/average.markCount).toString().length>4)(average.markSum/average.markCount).toString().substring(0,4)
        else if (average.markCount>0) (average.markSum/average.markCount).toString()
        else "0.0"
        item {
            Column(modifier = Modifier.padding(15.dp)) {
                Text(text = "Ср. оценка: " + aver)
                Text(text = "Пропуски: "+average.hours)
            }
        }
        items(diciplineList.value) {
                dicipline ->
            Card(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .fillMaxWidth()
                    .clickable {
                        openDialog.value = true
                        dialogTitle.value = dicipline.nameAbbv
                        diciplineObject.value = dicipline
                    }
            )
            {
                Text(
                    text = dicipline.nameAbbv,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(5.dp)
                )
                Divider(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column() {
                        Text(text = "ЛК")
                        Text(text = dicipline.lkM.toString() + " шт.")
                        Text(text = dicipline.lkH.toString() + " ч.")
                    }
                    Column() {
                        Text(text = "ПЗ")
                        Text(text = dicipline.pzM.toString() + " шт.")
                        Text(text = dicipline.pzH.toString() + " ч.")
                    }
                    Column() {
                        Text(text = "ЛР")
                        Text(text = dicipline.lbM.toString() + " шт.")
                        Text(text = dicipline.lbH.toString() + " ч.")
                    }
                }
            }

        }

    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = { Text(text = dialogTitle.value) },
            text = { Column() {
                Text("ПЗ: "+diciplineObject.value.pzMarks.toString().removePrefix("[").removeSuffix("]"))
                Text("ЛР: "+diciplineObject.value.lrMarks.toString().removePrefix("[").removeSuffix("]"))
                Text("ЛК: "+diciplineObject.value.lkMarks.toString().removePrefix("[").removeSuffix("]"))
            }},
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.inverseSurface
                    ),
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Oк")
                }
            }
        )
    }
}