package by.g_alex.mobile_iis.presentation.announcement_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AnnouncementScreen(
    viewModel: AnnouncementViewModel = hiltViewModel()
) {
    val state =viewModel.state.value
//    listOf<AnnouncemntDto>(AnnouncemntDto("333-4к",
//       "Консультация по твоей мамаше",
//    "12.12.1212",
//    "Cкиба Х.У",
//    listOf("sdcsd","csdsc"),
//    "11.00",
//    233,
//    "10.00",
//    null,
//   "sdcs"))//
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Объявления", fontSize = 20.sp) }
            )
        }
    ) {

        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if(state.anonsState == null||state.anonsState.isEmpty()){
                Text(text = "no announcement found", modifier = Modifier.align(Alignment.Center))
            }
            state.anonsState?.onEach {
                Card(
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .fillMaxWidth()
                ) {
                    Column(modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()) {
                        Text(text = it.content, fontWeight = FontWeight.Bold,modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp), textAlign = TextAlign.Center, fontSize = 20.sp)
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = it.startTime + " - " + it.endTime)
                            Text(text = it.date)
                        }
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                            Text(
                                text = it.auditory
                            )

                            Text(
                                text = it.employee
                            )
                        }
                    }
                }
            }
        }
    }
}