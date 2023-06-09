package by.g_alex.mobile_iis.presentation.dormitory_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PrivilegeList(
    viewModel: DormitoryViewModel = hiltViewModel()
) {
    Column(modifier = Modifier.fillMaxSize()) {
        if (viewModel.prState.value
                .privilegeState?.isEmpty() == true
        )
            Text(
                "Льгот нет",
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                textAlign = TextAlign.Center
            )
        for (n in viewModel.prState.value.privilegeState ?: emptyList()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = n.dormitoryPrivilegeCategoryName,
                            modifier = Modifier.padding(horizontal = 15.dp),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = n.year.toString(),
                            modifier = Modifier.padding(horizontal = 15.dp),
                            fontSize = 20.sp
                        )

                    }
                    Text(
                        text = n.dormitoryPrivilegeName,
                        modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                    )
                }
            }
        }
    }
}