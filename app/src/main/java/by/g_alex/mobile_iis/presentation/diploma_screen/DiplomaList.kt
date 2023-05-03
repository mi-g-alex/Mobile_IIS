package by.g_alex.mobile_iis.presentation.diploma_screen

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
fun DiplomaList(
    viewModel: DiplomaViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (viewModel.state.value.diplomaState?.isEmpty() == true)
            Text(
                "Пусто...",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                textAlign = TextAlign.Center
            )
        for (n in viewModel.state.value.diplomaState ?: emptyList()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            ) {
                Column {
                    Text(n.toString())
                }
            }
        }
    }
}