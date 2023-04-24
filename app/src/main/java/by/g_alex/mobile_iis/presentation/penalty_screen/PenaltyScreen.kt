package by.g_alex.mobile_iis.presentation.penalty_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FinesScreen(
    viewModel: PenaltyViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Взыскания", fontSize = 20.sp) }
            )
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            if (viewModel.state.value.PenaltyState?.isEmpty() == true)
                Text(
                    "Нет взысканий((",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            viewModel.state.value.PenaltyState?.onEach { penal ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Text(penal.penaltyType, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Text(penal.penaltyDate, fontSize = 20.sp)
                    }
                    Text(penal.reason, modifier = Modifier.padding(10.dp))
                }
            }
        }
    }
}