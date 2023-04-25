package by.g_alex.mobile_iis.presentation.settings.advance_screens.change_bio

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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import by.g_alex.mobile_iis.presentation.login_screen.restore_password_screen.select_how_restore.RestorePasswordSelectViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ChangeBioDialog(
    setShowDialog: (Boolean) -> Unit,
    viewModel: ChangeBioViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val inputText = remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val cnt = LocalContext.current

    LaunchedEffect(state.information) {
        if(state.information != null ) {
            setShowDialog(false)
            state.information = null
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
                OutlinedTextField(
                    value = inputText.value,
                    label = { Text(text = "Основная информация") },
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
                            viewModel.changeBio1(bio = inputText.value.text)
                        }
                    )
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    onClick = {
                            viewModel.changeBio1(bio = inputText.value.text)
                    }) {
                    if (state.isLoading) {
                        Text("Сохранение...")
                    } else if (state.error.isNotBlank()) {
                        Text("Ошибка. Повторить попытку")
                    } else {
                        Text("Сохранить")
                    }
                }
            }
        }
    }
}