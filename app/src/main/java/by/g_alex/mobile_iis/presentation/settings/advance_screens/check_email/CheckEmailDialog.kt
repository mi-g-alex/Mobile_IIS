package by.g_alex.mobile_iis.presentation.settings.advance_screens.check_email

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.mobile_iis.R

@Composable
fun CheckEmailDialog(
    setShowDialog: (Boolean) -> Unit,
    viewModel: CheckEmailViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getEmail()
    }
    val clipboardManager: androidx.compose.ui.platform.ClipboardManager = LocalClipboardManager.current

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = CenterHorizontally
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 15.dp) ) {
                    Text(text = viewModel.email.value, fontSize = 20.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.copy_text_icon),
                        contentDescription = "Копировать почту",
                        Modifier.clickable { clipboardManager.setText(AnnotatedString((viewModel.email.value))) }
                    )
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 15.dp) ) {
                    Text(text = viewModel.password.value, fontSize = 20.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.copy_text_icon),
                        contentDescription = "Копировать пароль",
                        Modifier.clickable {
                            clipboardManager.setText(AnnotatedString((viewModel.password.value)))
                        }
                    )
                }
            }
        }
    }
}