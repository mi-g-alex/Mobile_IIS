package by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore.RestorePasswordSelectViewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun RestorePasswordSelectDialog(
    setShowDialog: (Boolean) -> Unit,
    whatNeedToInputId: Int,
    value: String,
    login: String,
    navController: NavController,
    viewModel: RestorePasswordSelectViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val inputText = remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val cnt = LocalContext.current

    LaunchedEffect(state) {
        if (state.information != null) {
            val jsonData = Json.encodeToString(state.information)
            navController.navigate("restorePasswordEnd/${jsonData}/${login}")
            setShowDialog(false)
        }
        if(state.error.isNotBlank()) {
            Toast.makeText(cnt, "Не найдено", Toast.LENGTH_LONG).show()
        }
    }


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
                var s = "Введите "
                when (whatNeedToInputId) {
                    4 -> s += "номер телефона в формате $value"
                    6 -> s = "почту в формате $value"
                }
                Text(
                    text = s,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = inputText.value,
                    label = { Text(text = value) },
                    onValueChange = { inputText.value = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            var reg = ("^$value$")
                            reg = reg.replace("+", "\\+")
                            reg = reg.replace(".", "\\.")
                            reg = reg.replace("*******", "[A-Za-z0-9._%+-]*")
                            val regex = Regex(reg)
                            val matches = regex.matches(inputText.value.text)

                            if (matches) {
                                viewModel.restorePasswordCheck(login, inputText.value.text)
                                keyboardController?.hide()
                            } else {
                                Toast.makeText(cnt, "Неправильный формат", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    onClick = {
                        var reg = ("^$value$")
                        reg = reg.replace("+", "\\+")
                        reg = reg.replace(".", "\\.")
                        reg = reg.replace("*******", "[A-Za-z0-9._%+-]*")
                        val regex = Regex(reg)
                        val matches = regex.matches(inputText.value.text)

                        if (matches) {
                            viewModel.restorePasswordCheck(login, inputText.value.text)
                            keyboardController?.hide()
                        } else {
                            Toast.makeText(cnt, "Неправильный формат", Toast.LENGTH_SHORT).show()
                        }

                    }) {
                    Text("Продолжить")
                }
            }
        }
    }
}