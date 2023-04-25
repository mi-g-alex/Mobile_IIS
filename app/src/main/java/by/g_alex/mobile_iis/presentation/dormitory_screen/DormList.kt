package by.g_alex.mobile_iis.presentation.dormitory_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DormList(
    viewModel: DormitoryViewModel = hiltViewModel()
){
    Column(modifier = Modifier.fillMaxSize()) {
        if(viewModel.state.value.dormState?.isEmpty() == true)
            Text("Пусто...",fontSize=20.sp, modifier = Modifier.fillMaxSize().padding(10.dp), textAlign = TextAlign.Center)
        for (n in viewModel.state.value.dormState ?: emptyList()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            ) {
                Column() {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp), Arrangement.Center
                    ) {
                        Text(n.status ?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    }
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(text = "Дата подачи: ")
                        Text(
                            text = SimpleDateFormat(
                                "dd.MM.yyyy",
                                Locale.getDefault()
                            ).format(Date(n.acceptedDate ?: 0))
                        )
                    }
                    Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                        Text(text = "Дата заcеления: ")
                        Text(
                            text = SimpleDateFormat(
                                "dd.MM.yyyy",
                                Locale.getDefault()
                            ).format(Date(n.settledDate ?: 0))
                        )
                    }
                    Text(
                        text = n.roomInfo ?: "",
                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                    )
                }
            }
        }
    }
}