package by.g_alex.mobile_iis.presentation.study_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import by.g_alex.mobile_iis.R

@Composable
fun SelectCertificateOrMarkSheetDialog(
    setShowDialog: (Boolean) -> Unit,
    navController: NavController,
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(Modifier.padding(5.dp)) {
                Text(text = "Заказать...", fontSize = 30.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("")
                            setShowDialog(false)
                        }.padding(vertical = 5.dp),
                    Arrangement.SpaceBetween) {
                    Text("Ведомостичку", fontSize = 30.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.next_icon),
                        contentDescription = null
                    )
                }
                Divider(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .height(1.dp)
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.outline
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("addCertificate")
                            setShowDialog(false)
                        }.padding(vertical = 5.dp),
                    Arrangement.SpaceBetween) {
                    Text("Справку", fontSize = 30.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.next_icon),
                        contentDescription = null
                    )
                }
            }
        }
    }
}